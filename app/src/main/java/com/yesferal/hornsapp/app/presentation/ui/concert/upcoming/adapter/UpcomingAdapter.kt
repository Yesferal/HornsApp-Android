package com.yesferal.hornsapp.app.presentation.ui.concert.upcoming.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yesferal.hornsapp.app.presentation.common.ViewData
import com.yesferal.hornsapp.app.presentation.ui.concert.upcoming.FiltersViewData
import com.yesferal.hornsapp.app.presentation.ui.concert.upcoming.ErrorViewData
import com.yesferal.hornsapp.app.presentation.ui.concert.upcoming.UpcomingViewData
import com.yesferal.hornsapp.app.presentation.ui.filters.FiltersViewHolder

class UpcomingAdapter (
    private val listener: Listener,
    private val list: MutableList<ViewData> = mutableListOf()
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    interface Listener :
        FiltersViewHolder.Listener,
        UpcomingViewHolder.Listener

    enum class Key(val value: Int) {
        FILTERS(1),
        ERROR(2),
        CONCERT(3)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        return when (viewType) {
            Key.FILTERS.value -> {
                FiltersViewHolder(parent, listener)
            }
            Key.ERROR.value -> {
                ErrorViewHolder(parent)
            }
            else -> {
                UpcomingViewHolder(parent, listener)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when(list[position]) {
            is FiltersViewData -> {
                Key.FILTERS.value
            }
            is ErrorViewData -> {
                Key.ERROR.value
            }
            else -> {
                Key.CONCERT.value
            }
        }
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int
    ) {
        when (val view = list[position]) {
            is FiltersViewData -> {
                (holder as FiltersViewHolder).bind(view)
            }
            is UpcomingViewData -> {
                (holder as UpcomingViewHolder).bind(view)
            }
            is ErrorViewData -> {
                (holder as ErrorViewHolder).bind(view)
            }
        }
    }

    fun setItems(list: List<ViewData>) {
        this.list.clear()
        this.list.addAll(list)

        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = list.size
}