package com.example.petmate.core.decorator

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class HorizontalDividerItemDecorator(private var dividerWidth: Int): RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        applyHorizontalOffsets(outRect)
    }

    private fun applyHorizontalOffsets(outRect: Rect) {
        outRect.left = dividerWidth
        outRect.right = dividerWidth
    }
}
