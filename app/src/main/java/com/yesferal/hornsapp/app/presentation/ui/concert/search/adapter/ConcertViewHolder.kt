package com.yesferal.hornsapp.app.presentation.ui.concert.search.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yesferal.hornsapp.app.R
import com.yesferal.hornsapp.app.presentation.common.custom.load
import com.yesferal.hornsapp.app.presentation.common.custom.setAllCornersRounded
import com.yesferal.hornsapp.app.presentation.common.custom.setUpWith
import com.yesferal.hornsapp.app.presentation.ui.concert.search.ConcertViewData
import kotlinx.android.synthetic.main.custom_date_text_view.view.*
import kotlinx.android.synthetic.main.item_concert.view.*

class ConcertViewHolder constructor(
    itemView: View,
    private val listener: ConcertAdapter.Listener
) : RecyclerView.ViewHolder(itemView) {

    constructor(
        parent: ViewGroup,
        listener: ConcertAdapter.Listener
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
            listener.onConcertClick(viewData)
        }
    }
}