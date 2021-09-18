package com.yesferal.hornsapp.app.presentation.common.custom

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class RecyclerViewVerticalDecorator (
    private val paddingTop: Int = 0,
    private val paddingBottom: Int = 0
): RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        if (parent.context == null) { return }

        val itemPosition = parent.getChildAdapterPosition(view)
        val density = parent.context.resources.displayMetrics.density

        if (itemPosition == 0) {
            outRect.top = (density * paddingTop).toInt()
        }

        // AdHeight should be 50dp: This is equals to Ad size (50 + 16)
        val adapter = parent.adapter
        if (adapter != null && itemPosition == adapter.itemCount - 1) {
            val adHeight = 50
            outRect.bottom = (density * (adHeight + paddingBottom)).toInt()
        }
    }
}
