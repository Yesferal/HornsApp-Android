package com.yesferal.hornsapp.app.presentation.concert.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yesferal.hornsapp.app.R
import com.yesferal.hornsapp.app.util.load
import com.yesferal.hornsapp.app.util.setAllCornersRounded
import com.yesferal.hornsapp.app.util.setUpWith
import com.yesferal.hornsapp.domain.entity.Concert
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

    fun bind(concert: Concert) {
        itemView.titleTextView.setUpWith(concert.name)
        itemView.dayTextView.setUpWith(concert.day)
        itemView.monthTextView.setUpWith(concert.month)

        itemView.concertImageView.setAllCornersRounded()
        itemView.concertImageView.load(concert.headlinerImage)

        itemView.timeTextView.setUpWith(concert.time)
        itemView.genreTextView.setUpWith(concert.genre)

        itemView.containerLayout.setOnClickListener {
            listener.onConcertItemClick(concert)
        }
    }
}