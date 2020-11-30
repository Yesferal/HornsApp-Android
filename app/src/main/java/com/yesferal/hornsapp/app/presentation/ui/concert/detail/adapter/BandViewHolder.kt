package com.yesferal.hornsapp.app.presentation.ui.concert.detail.adapter

import android.view.View
import com.yesferal.hornsapp.app.presentation.common.custom.load
import com.yesferal.hornsapp.app.presentation.common.custom.setAllCornersRounded
import com.yesferal.hornsapp.app.presentation.common.multitype.BaseViewHolder
import com.yesferal.hornsapp.app.presentation.common.multitype.ViewHolderBinding
import com.yesferal.hornsapp.app.presentation.ui.concert.detail.BandViewData
import kotlinx.android.synthetic.main.item_band.view.*

class BandViewHolder (
    itemView: View,
    listener: ViewHolderBinding.Listener
) : BaseViewHolder<BandViewData>(itemView, listener) {

    override fun bind(viewData: BandViewData) {
        itemView.itemTextView.text = viewData.name
        itemView.itemImageView.setAllCornersRounded()
        itemView.itemImageView.load(viewData.membersImage)

        itemView.itemImageView.setOnClickListener {
            (listener as BandViewData.Listener).onClick(viewData)
        }
    }
}