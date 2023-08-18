package com.example.petmate

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class RightItemDecorator (var divWidth: Int) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)
        outRect.right = divWidth
    }
}