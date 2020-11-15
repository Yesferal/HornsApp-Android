package com.yesferal.hornsapp.app.presentation.ui.concert.upcoming.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yesferal.hornsapp.app.R
import com.yesferal.hornsapp.app.presentation.common.ui.custom.load
import com.yesferal.hornsapp.app.presentation.common.ui.custom.setAllCornersRounded
import com.yesferal.hornsapp.app.presentation.common.ui.custom.setUpWith
import com.yesferal.hornsapp.app.presentation.ui.concert.upcoming.ConcertViewData
import kotlinx.android.synthetic.main.custom_date_text_view.view.*
import kotlinx.android.synthetic.main.item_concert.view.*

class ConcertViewHolder(
    itemView: View,
    private val listener: Listener
) : RecyclerView.ViewHolder(itemView) {

    interface Listener {
        fun onClick(concertViewData: ConcertViewData)
    }

    constructor(
        parent: ViewGroup,
        listener: Listener
    ) : this(
        LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_concert, parent, false),
        listener
    )

    fun bind(viewData: ConcertViewData) {
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
            listener.onClick(viewData)
        }
    }
}