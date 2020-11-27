package com.yesferal.hornsapp.app.presentation.ui.concert.newest.adapter

import android.view.View
import com.yesferal.hornsapp.app.presentation.common.multitype.ViewHolderData
import com.yesferal.hornsapp.app.presentation.common.multitype.HornsViewHolder
import com.yesferal.hornsapp.app.presentation.common.custom.setUpWith
import com.yesferal.hornsapp.app.presentation.ui.concert.newest.NewestViewData
import kotlinx.android.synthetic.main.custom_date_text_view.view.*
import kotlinx.android.synthetic.main.item_newest.view.*

class NewestViewHolder (
    itemView: View,
    listener: ViewHolderData.Listener
) : HornsViewHolder<NewestViewData>(itemView, listener) {

    override fun bind(viewData: NewestViewData) {
        itemView.containerLayout.setOnClickListener {
            (listener as NewestViewData.Listener).onClick(viewData)
        }

        itemView.titleTextView.setUpWith(viewData.name)
        itemView.subtitleTextView.setUpWith(viewData.ticketingHostName)

        itemView.dayTextView.setUpWith(viewData.day)
        itemView.monthTextView.setUpWith(viewData.month)
    }
}