package com.yesferal.hornsapp.app.presentation.common.ui.custom

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class RecyclerViewHorizontalDecorator(
    private val padding: Int = 8
): RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        if (parent.context == null) { return }

        val itemPosition = parent.getChildAdapterPosition(view)

        if (itemPosition == 0) {
            val density = parent.context.resources.displayMetrics.density
            outRect.left = (density * padding).toInt()
        }

        val adapter = parent.adapter
        if (adapter != null && itemPosition == adapter.itemCount - 1) {
            val density = parent.context.resources.displayMetrics.density
            outRect.right = (density * padding).toInt()
        }
    }
}