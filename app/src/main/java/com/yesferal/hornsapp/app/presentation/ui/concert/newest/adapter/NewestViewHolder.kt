package com.yesferal.hornsapp.app.presentation.ui.concert.newest.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yesferal.hornsapp.app.R
import com.yesferal.hornsapp.app.presentation.common.custom.setUpWith
import com.yesferal.hornsapp.app.presentation.ui.concert.newest.NewestViewData
import kotlinx.android.synthetic.main.custom_date_text_view.view.*
import kotlinx.android.synthetic.main.item_newest.view.*

class NewestViewHolder (
    itemView: View,
    private val listener: Listener
) : RecyclerView.ViewHolder(itemView) {

    interface Listener {
        fun onClick(newestViewData: NewestViewData)
    }

    constructor(
        parent: ViewGroup,
        listener: Listener
    ) : this(
        LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_newest, parent, false),
        listener
    )

    fun bind(viewData: NewestViewData) {
        itemView.containerLayout.setOnClickListener {
            listener.onClick(viewData)
        }

        itemView.titleTextView.setUpWith(viewData.name)
        itemView.subtitleTextView.setUpWith(viewData.ticketingHostName)

        itemView.dayTextView.setUpWith(viewData.day)
        itemView.monthTextView.setUpWith(viewData.month)
    }
}