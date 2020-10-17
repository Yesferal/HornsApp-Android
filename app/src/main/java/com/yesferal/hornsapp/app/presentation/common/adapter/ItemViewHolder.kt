package com.yesferal.hornsapp.app.presentation.common.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yesferal.hornsapp.app.R
import com.yesferal.hornsapp.app.presentation.common.custom.load
import com.yesferal.hornsapp.app.presentation.common.custom.setAllCornersRounded
import kotlinx.android.synthetic.main.item_base.view.*

class ItemViewHolder (
    itemView: View,
    private val listener: ItemAdapter.Listener
) : RecyclerView.ViewHolder(itemView)  {

    constructor(
        parent: ViewGroup,
        listener: ItemAdapter.Listener
    ) : this(
        LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_base, parent, false),
        listener
    )

    fun bind(item: Item) {
        itemView.itemTextView.text = item.name
        itemView.itemImageView.setAllCornersRounded()
        itemView.itemImageView.load(item.imageUrl)

        itemView.itemImageView.setOnClickListener {
            listener.onClick(item)
        }
    }
}