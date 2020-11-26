package com.yesferal.hornsapp.app.presentation.ui.concert.newest.adapter

import android.view.View
import com.yesferal.hornsapp.app.presentation.common.ui.multitype.HornsViewHolder
import com.yesferal.hornsapp.app.presentation.common.ui.custom.setUpWith
import com.yesferal.hornsapp.app.presentation.ui.concert.newest.NewestTitleViewData
import kotlinx.android.synthetic.main.item_newest_title.view.*

class NewestTitleViewHolder(
    itemView: View
) : HornsViewHolder<NewestTitleViewData>(itemView) {

    override fun bind(viewData: NewestTitleViewData) {
        itemView.titleTextView.setUpWith(viewData.name)
    }
}