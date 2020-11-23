package com.yesferal.hornsapp.app.presentation.ui.concert.newest.adapter

import android.view.View
import com.yesferal.hornsapp.app.presentation.common.ui.custom.HornsViewHolder
import com.yesferal.hornsapp.app.presentation.common.ui.custom.setUpWith
import com.yesferal.hornsapp.app.presentation.ui.concert.newest.TextViewData
import kotlinx.android.synthetic.main.item_newest_title.view.*

class NewestTitleViewHolder(
    itemView: View
) : HornsViewHolder<TextViewData>(itemView) {

    override fun bind(viewData: TextViewData) {
        itemView.titleTextView.setUpWith(viewData.name)
    }
}