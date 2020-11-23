package com.yesferal.hornsapp.app.presentation.ui.concert.upcoming.adapter

import android.view.View
import com.yesferal.hornsapp.app.presentation.common.ViewHolderData
import com.yesferal.hornsapp.app.presentation.common.ui.custom.HornsViewHolder
import com.yesferal.hornsapp.app.presentation.common.ui.custom.load
import com.yesferal.hornsapp.app.presentation.common.ui.custom.setAllCornersRounded
import com.yesferal.hornsapp.app.presentation.common.ui.custom.setUpWith
import com.yesferal.hornsapp.app.presentation.ui.concert.upcoming.UpcomingViewData
import kotlinx.android.synthetic.main.custom_date_text_view.view.*
import kotlinx.android.synthetic.main.item_upcoming.view.*

class UpcomingViewHolder(
    itemView: View,
    listener: ViewHolderData.Listener
) : HornsViewHolder<UpcomingViewData>(itemView, listener) {

    override fun bind(viewData: UpcomingViewData) {
        viewData.year?.let {
            itemView.tagTextView.setUpWith("#$it")
        }
        itemView.titleTextView.setUpWith(viewData.name)
        itemView.dayTextView.setUpWith(viewData.day)
        itemView.monthTextView.setUpWith(viewData.month)
        itemView.timeTextView.setUpWith(viewData.time)
        itemView.genreTextView.setUpWith(viewData.genre)

        itemView.concertImageView.setAllCornersRounded()
        itemView.concertImageView.load(viewData.image)
        
        itemView.containerLayout.setOnClickListener {
            (listener as UpcomingViewData.Listener).onClick(viewData)
        }
    }
}