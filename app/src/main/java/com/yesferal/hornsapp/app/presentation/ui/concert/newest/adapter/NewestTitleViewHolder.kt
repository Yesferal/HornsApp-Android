package com.yesferal.hornsapp.app.presentation.ui.concert.newest.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yesferal.hornsapp.app.R
import com.yesferal.hornsapp.app.presentation.common.TitleViewData
import com.yesferal.hornsapp.app.presentation.common.custom.setUpWith
import kotlinx.android.synthetic.main.item_upcoming_title.view.*

class NewestTitleViewHolder(
    itemView: View
) : RecyclerView.ViewHolder(itemView) {
    constructor(
        parent: ViewGroup
    ) : this(
        LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_upcoming_title, parent, false)
    )

    fun bind(viewData: TitleViewData) {
        itemView.titleTextView.setUpWith(viewData.name)
    }
}