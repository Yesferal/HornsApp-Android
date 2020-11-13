package com.yesferal.hornsapp.app.presentation.ui.concert.search.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yesferal.hornsapp.app.R
import com.yesferal.hornsapp.app.presentation.common.custom.setUpWith
import kotlinx.android.synthetic.main.item_filter.view.*

class FilterViewHolder(
    itemView: View,
    private val listener: Listener
) : RecyclerView.ViewHolder(itemView) {

    interface Listener {
        fun onClick(textViewData: CategoryViewData)
    }

    constructor(
        parent: ViewGroup,
        listener: Listener
    ) : this(
        LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_filter, parent, false),
        listener
    )

    fun bind(viewData: CategoryViewData) {
        itemView.nameTextView.setUpWith(viewData.name)
        itemView.containerLayout.setOnClickListener {
            listener.onClick(viewData)
        }
    }
}