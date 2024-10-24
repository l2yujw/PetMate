package com.example.petmate.core.classifier

import android.content.Context
import android.graphics.Bitmap
import org.tensorflow.lite.support.common.FileUtil
import org.tensorflow.lite.support.common.ops.NormalizeOp
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.support.image.ops.ResizeOp
import org.tensorflow.lite.support.label.TensorLabel
import org.tensorflow.lite.support.model.Model
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer

class Classifier(private val context: Context, private val modelName: String) {
    private lateinit var model: Model
    private lateinit var inputImage: TensorImage
    private lateinit var outputBuffer: TensorBuffer
    private var modelInputChannel = 0
    private var modelInputWidth = 0
    private var modelInputHeight = 0
    private val labels = mutableListOf<String>()

    // 모델 초기화
    fun init() {
        model = Model.createModel(context, modelName)
        initModelShape()
        labels.addAll(FileUtil.loadLabels(context, LABEL_FILE))
    }

    // 모델의 입력 및 출력 텐서 모양 설정
    private fun initModelShape() {
        val inputTensor = model.getInputTensor(0)
        val inputShape = inputTensor.shape()
        modelInputChannel = inputShape[0]
        modelInputWidth = inputShape[1]
        modelInputHeight = inputShape[2]

        inputImage = TensorImage(inputTensor.dataType())

        val outputTensor = model.getOutputTensor(0)
        outputBuffer = TensorBuffer.createFixedSize(outputTensor.shape(), outputTensor.dataType())
    }

    // 이미지 분류
    fun classify(image: Bitmap): Pair<String, Float> {
        inputImage = loadImage(image)
        val inputs = arrayOf(inputImage.buffer)
        val outputs = mutableMapOf<Int, Any>()
        outputs[0] = outputBuffer.buffer.rewind()
        model.run(inputs, outputs)

        val output = TensorLabel(labels, outputBuffer).mapWithFloatValue
        return argmax(output)
    }

    // 이미지 처리: 크기 조정 및 정규화
    private fun loadImage(bitmap: Bitmap): TensorImage {
        inputImage.load(if (bitmap.config != Bitmap.Config.ARGB_8888) convertBitmapToARGB8888(bitmap) else bitmap)

        val imageProcessor = org.tensorflow.lite.support.image.ImageProcessor.Builder()
            .add(ResizeOp(modelInputWidth, modelInputHeight, ResizeOp.ResizeMethod.NEAREST_NEIGHBOR))
            .add(NormalizeOp(0.0f, 255.0f))
            .build()

        return imageProcessor.process(inputImage)
    }

    // Bitmap을 ARGB_8888로 변환
    private fun convertBitmapToARGB8888(bitmap: Bitmap) = bitmap.copy(Bitmap.Config.ARGB_8888, true)

    // 최대값을 갖는 결과 반환
    private fun argmax(map: Map<String, Float>): Pair<String, Float> =
        map.maxByOrNull { it.value }?.let { it.key to it.value } ?: "" to 0f

    // 모델 리소스 정리
    fun finish() {
        if (::model.isInitialized) model.close()
    }

    companion object {
        const val LABEL_FILE = "labels.txt"
    }
}
