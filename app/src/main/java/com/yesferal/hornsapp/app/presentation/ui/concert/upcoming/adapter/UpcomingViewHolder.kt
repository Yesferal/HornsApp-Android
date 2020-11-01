package com.yesferal.hornsapp.app.presentation.ui.concert.upcoming.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yesferal.hornsapp.app.R
import com.yesferal.hornsapp.app.presentation.common.custom.setUpWith
import com.yesferal.hornsapp.app.presentation.ui.concert.search.adapter.ConcertAdapter
import com.yesferal.hornsapp.app.presentation.ui.concert.upcoming.UpcomingViewData
import kotlinx.android.synthetic.main.custom_date_text_view.view.*
import kotlinx.android.synthetic.main.item_upcoming.view.*

class UpcomingViewHolder (
    itemView: View,
    private val listener: ConcertAdapter.Listener
) : RecyclerView.ViewHolder(itemView) {

    constructor(
        parent: ViewGroup,
        listener: ConcertAdapter.Listener
    ) : this(
        LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_upcoming, parent, false),
        listener
    )

    fun bind(viewData: UpcomingViewData) {
        itemView.containerLayout.setOnClickListener {
            listener.onConcertClick(viewData)
        }

        itemView.titleTextView.setUpWith(viewData.name)
        itemView.subtitleTextView.setUpWith(viewData.ticketingHostName)

        itemView.dayTextView.setUpWith(viewData.day)
        itemView.monthTextView.setUpWith(viewData.month)
    }
}