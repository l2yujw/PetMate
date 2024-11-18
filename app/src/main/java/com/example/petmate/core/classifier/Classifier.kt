package com.example.petmate.core.classifier

import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import org.tensorflow.lite.support.common.FileUtil
import org.tensorflow.lite.support.common.ops.NormalizeOp
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.support.image.ops.ResizeOp
import org.tensorflow.lite.support.label.TensorLabel
import org.tensorflow.lite.support.model.Model
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import java.io.IOException

class Classifier(private val context: Context, private val modelName: String) {

    private lateinit var model: Model
    private lateinit var inputImage: TensorImage
    private lateinit var outputBuffer: TensorBuffer
    private var modelInputChannel = 0
    private var modelInputWidth = 0
    private var modelInputHeight = 0
    private val labels = mutableListOf<String>()

    // 모델 초기화
    fun initializeModel() {
        try {
            model = Model.createModel(context, modelName)
            initModelShape()
            loadLabels()
        } catch (e: Exception) {
            Log.e("Classifier", "Failed to initialize the model: ${e.message}")
        }
    }

    // 라벨 로드
    private fun loadLabels() {
        try {
            labels.addAll(FileUtil.loadLabels(context, LABELS_FILE))
        } catch (e: IOException) {
            Log.e("Classifier", "Failed to load labels: ${e.message}")
        }
    }

    // 모델의 입력 및 출력 텐서 모양 설정
    private fun initModelShape() {
        setInputTensorShape()
        setOutputTensorShape()
    }

    // 입력 텐서 모양 설정
    private fun setInputTensorShape() {
        val inputTensor = model.getInputTensor(0)
        val inputShape = inputTensor.shape()
        modelInputChannel = inputShape[0]
        modelInputWidth = inputShape[1]
        modelInputHeight = inputShape[2]
        inputImage = TensorImage(inputTensor.dataType())
    }

    // 출력 텐서 모양 설정
    private fun setOutputTensorShape() {
        val outputTensor = model.getOutputTensor(0)
        outputBuffer = TensorBuffer.createFixedSize(outputTensor.shape(), outputTensor.dataType())
    }

    // 이미지 분류
    fun classify(image: Bitmap): ClassificationResult {
        val preparedImage = prepareInputImage(image)
        executeModel(preparedImage)
        return extractClassificationResult()
    }

    // 입력 이미지를 처리 및 전처리
    private fun prepareInputImage(image: Bitmap): TensorImage {
        val loadedImage = loadImage(image)
        return resizeAndNormalizeImage(loadedImage)
    }

    // Bitmap 이미지 로딩
    private fun loadImage(image: Bitmap): TensorImage {
        val bitmapARGB = ensureBitmapConfig(image, Bitmap.Config.ARGB_8888)
        inputImage.load(bitmapARGB)
        return inputImage
    }

    // Bitmap이 지정된 형식인지 확인하고 변환
    private fun ensureBitmapConfig(bitmap: Bitmap, config: Bitmap.Config): Bitmap {
        return if (bitmap.config != config) convertBitmapToARGB8888(bitmap) else bitmap
    }

    // 모델 실행
    private fun executeModel(preparedImage: TensorImage) {
        val inputs = arrayOf(preparedImage.buffer)
        val outputs = mutableMapOf<Int, Any>()
        outputs[0] = outputBuffer.buffer.rewind()
        model.run(inputs, outputs)
    }

    // 이미지 처리: 크기 조정 및 정규화
    private fun resizeAndNormalizeImage(inputImage: TensorImage): TensorImage {
        val imageProcessor = org.tensorflow.lite.support.image.ImageProcessor.Builder()
            .add(ResizeOp(modelInputWidth, modelInputHeight, ResizeOp.ResizeMethod.NEAREST_NEIGHBOR))
            .add(NormalizeOp(IMAGE_NORMALIZATION_MIN, IMAGE_NORMALIZATION_MAX))
            .build()
        return imageProcessor.process(inputImage)
    }

    // 출력값 처리 및 최대값 추출
    private fun extractClassificationResult(): ClassificationResult {
        val output = TensorLabel(labels, outputBuffer).mapWithFloatValue
        val (label, confidence) = findMaxLabel(output)
        return ClassificationResult(label, confidence)
    }

    // Bitmap을 ARGB_8888로 변환
    private fun convertBitmapToARGB8888(bitmap: Bitmap) = bitmap.copy(Bitmap.Config.ARGB_8888, true)

    // 최대값을 갖는 결과 반환
    private fun findMaxLabel(map: Map<String, Float>): Pair<String, Float> =
        map.maxByOrNull { it.value }?.let { it.key to it.value } ?: "" to 0f

    // 모델 리소스 정리
    fun closeModel() {
        if (::model.isInitialized) {
            model.close()
        }
    }

    companion object {
        const val LABELS_FILE = "labels.txt"
        const val IMAGENET_CLASSIFY_MODEL = "model_unquant.tflite"
        const val IMAGE_NORMALIZATION_MIN = 0.0f
        const val IMAGE_NORMALIZATION_MAX = 255.0f
    }
}

// 데이터 클래스: 분류 결과를 표현
data class ClassificationResult(
    val label: String,
    val confidence: Float
)
