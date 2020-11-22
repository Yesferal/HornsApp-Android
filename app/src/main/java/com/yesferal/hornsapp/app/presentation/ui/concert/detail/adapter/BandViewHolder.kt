package com.yesferal.hornsapp.app.presentation.ui.concert.detail.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yesferal.hornsapp.app.R
import com.yesferal.hornsapp.app.presentation.common.ui.custom.load
import com.yesferal.hornsapp.app.presentation.common.ui.custom.setAllCornersRounded
import com.yesferal.hornsapp.app.presentation.ui.concert.detail.BandViewData
import kotlinx.android.synthetic.main.item_band.view.*

class BandViewHolder (
    itemView: View,
    private val listener: BandsAdapter.Listener
) : RecyclerView.ViewHolder(itemView)  {

    constructor(
        parent: ViewGroup,
        listener: BandsAdapter.Listener
    ) : this(
        LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_band, parent, false),
        listener
    )

    fun bind(bandViewData: BandViewData) {
        itemView.itemTextView.text = bandViewData.name
        itemView.itemImageView.setAllCornersRounded()
        itemView.itemImageView.load(bandViewData.membersImage)

        itemView.itemImageView.setOnClickListener {
            listener.onClick(bandViewData)
        }
    }
}