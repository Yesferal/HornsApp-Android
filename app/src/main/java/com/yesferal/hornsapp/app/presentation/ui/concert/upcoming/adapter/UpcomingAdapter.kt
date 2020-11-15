package com.yesferal.hornsapp.app.presentation.ui.concert.upcoming.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yesferal.hornsapp.app.presentation.common.ViewData
import com.yesferal.hornsapp.app.presentation.ui.concert.upcoming.ConcertViewData
import com.yesferal.hornsapp.app.presentation.ui.concert.upcoming.FiltersViewData
import com.yesferal.hornsapp.app.presentation.ui.filters.FiltersViewHolder

class UpcomingAdapter (
    private val listener: Listener,
    private val list: MutableList<ViewData> = mutableListOf()
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    interface Listener :
        FiltersViewHolder.Listener,
        ConcertViewHolder.Listener

    enum class Key(val value: Int) {
        FILTERS(1),
        CONCERT(2)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        return when (viewType) {
            Key.FILTERS.value -> {
                FiltersViewHolder(parent, listener)
            }
            else -> {
                ConcertViewHolder(parent, listener)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when(list[position]) {
            is FiltersViewData -> {
                Key.FILTERS.value
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
            is ConcertViewData -> {
                (holder as ConcertViewHolder).bind(view)
            }
        }
    }

    fun setCategories(filtersViewData: FiltersViewData) {
        this.list.removeAll(this.list.filterIsInstance<FiltersViewData>())
        this.list.add(0, filtersViewData)
    }

    fun setConcerts(list: List<ConcertViewData>) {
        this.list.removeAll(this.list.filterIsInstance<ConcertViewData>())
        this.list.addAll(list)

        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = list.size
}