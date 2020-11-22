package com.yesferal.hornsapp.app.presentation.ui.concert.upcoming.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yesferal.hornsapp.app.R
import com.yesferal.hornsapp.app.presentation.common.ui.custom.load
import com.yesferal.hornsapp.app.presentation.common.ui.custom.setAllCornersRounded
import com.yesferal.hornsapp.app.presentation.common.ui.custom.setUpWith
import com.yesferal.hornsapp.app.presentation.ui.concert.upcoming.UpcomingViewData
import kotlinx.android.synthetic.main.custom_date_text_view.view.*
import kotlinx.android.synthetic.main.item_upcoming.view.*

class UpcomingViewHolder(
    itemView: View,
    private val listener: Listener
) : RecyclerView.ViewHolder(itemView) {

    interface Listener {
        fun onClick(upcomingViewData: UpcomingViewData)
    }

    constructor(
        parent: ViewGroup,
        listener: Listener
    ) : this(
        LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_upcoming, parent, false),
        listener
    )

    fun bind(upcomingViewData: UpcomingViewData) {
        upcomingViewData.year?.let {
            itemView.tagTextView.setUpWith("#$it")
        }
        itemView.titleTextView.setUpWith(upcomingViewData.name)
        itemView.dayTextView.setUpWith(upcomingViewData.day)
        itemView.monthTextView.setUpWith(upcomingViewData.month)
        itemView.timeTextView.setUpWith(upcomingViewData.time)
        itemView.genreTextView.setUpWith(upcomingViewData.genre)

        itemView.concertImageView.setAllCornersRounded()
        itemView.concertImageView.load(upcomingViewData.image)
        
        itemView.containerLayout.setOnClickListener {
            listener.onClick(upcomingViewData)
        }
    }
}