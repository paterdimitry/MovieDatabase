package com.geekbrain.moviedatabase.ui.main

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration

//класс для создания разделителей списка заданной ширины

class HorizontalItemDecoration(private val horizontalSpaceWidth: Int) : ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        outRect.apply {
            right = horizontalSpaceWidth
            left = horizontalSpaceWidth
        }

    }
}
