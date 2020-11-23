package com.yesferal.hornsapp.app.presentation.common.ui.custom

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yesferal.hornsapp.app.R
import com.yesferal.hornsapp.app.presentation.common.ViewHolderData
import com.yesferal.hornsapp.app.presentation.ui.concert.newest.adapter.NewestTitleViewHolder
import com.yesferal.hornsapp.app.presentation.ui.concert.newest.adapter.NewestViewHolder
import com.yesferal.hornsapp.app.presentation.ui.concert.upcoming.adapter.ErrorViewHolder
import com.yesferal.hornsapp.app.presentation.ui.concert.upcoming.adapter.UpcomingViewHolder
import com.yesferal.hornsapp.app.presentation.ui.filters.FiltersViewHolder
import java.lang.Exception

abstract class HornsViewHolder<VIEW_DATA: ViewHolderData>(
    itemView: View,
    var listener: ViewHolderData.Listener? = null
) : RecyclerView.ViewHolder(itemView) {

    companion object {
        fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int,
            listener: ViewHolderData.Listener
        ): HornsViewHolder<ViewHolderData> {

            val itemView = LayoutInflater
                .from(parent.context)
                .inflate(viewType, parent, false)

            val viewHolder = when (viewType) {
                R.layout.item_filters -> {
                    FiltersViewHolder(itemView, listener)
                }
                R.layout.custom_error -> {
                    ErrorViewHolder(itemView)
                }
                R.layout.item_upcoming -> {
                    UpcomingViewHolder(itemView, listener)
                }
                R.layout.item_newest_title-> {
                    NewestTitleViewHolder(itemView)
                }
                R.layout.item_newest -> {
                    NewestViewHolder(itemView, listener)
                }
                else -> {
                    throw Exception("ViewType [$viewType] not found. Please implement in this [when] statement.")
                }
            }

            @Suppress("UNCHECKED_CAST")
            return viewHolder as HornsViewHolder<ViewHolderData>
        }
    }

    abstract fun bind(viewData: VIEW_DATA)
}