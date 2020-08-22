package com.yesferal.hornsapp.app.presentation.item.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yesferal.hornsapp.app.R
import com.yesferal.hornsapp.app.util.load
import com.yesferal.hornsapp.app.util.setTopCornersRounded
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

    fun bind(item: HornsAppItem) {
        itemView.itemTextView.text = item.name
        itemView.itemImageView.setTopCornersRounded(dp = 16)
        itemView.itemImageView.load(item.imageUrl)

        itemView.containerView.setOnClickListener {
            listener.onClick(item)
        }
    }
}