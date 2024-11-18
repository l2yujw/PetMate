package com.example.petmate.core.decorator

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class VerticalDividerItemDecorator(var dividerHeight: Int) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        applyVerticalOffsets(outRect)
    }

    // 위, 아래 구분선 설정 메서드
    private fun applyVerticalOffsets(outRect: Rect) {
        outRect.top = dividerHeight
        outRect.bottom = dividerHeight
    }
}
