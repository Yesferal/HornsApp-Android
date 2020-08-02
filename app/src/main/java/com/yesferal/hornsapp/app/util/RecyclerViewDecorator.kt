package com.yesferal.hornsapp.app.util

import android.content.Context
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class RecyclerViewDecorator (
    val context: Context?
): RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        if (context == null) { return }

        val itemPosition = parent.getChildAdapterPosition(view)
        val padding = 16

        if (itemPosition == 0) {
            val density = context.resources.displayMetrics.density
            outRect.left = (density * padding).toInt()
        }

        val adapter = parent.adapter
        if (adapter != null && itemPosition == adapter.itemCount - 1) {
            val density = context.resources.displayMetrics.density
            outRect.right = (density * padding).toInt()
        }
    }
}