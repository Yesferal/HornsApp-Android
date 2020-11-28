package com.yesferal.hornsapp.app.presentation.ui.concert.newest.adapter

import android.view.View
import com.yesferal.hornsapp.app.presentation.common.multitype.BaseViewHolder
import com.yesferal.hornsapp.app.presentation.common.custom.setUpWith
import com.yesferal.hornsapp.app.presentation.ui.concert.newest.TitleViewData
import kotlinx.android.synthetic.main.item_newest_title.view.*

class NewestTitleViewHolder(
    itemView: View
) : BaseViewHolder<TitleViewData>(itemView) {

    override fun bind(viewData: TitleViewData) {
        itemView.titleTextView.setUpWith(viewData.name)
    }
}