package com.yesferal.hornsapp.app.presentation.ui.favorite

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yesferal.hornsapp.app.presentation.ui.concert.upcoming.UpcomingViewData
import com.yesferal.hornsapp.app.presentation.ui.concert.upcoming.adapter.UpcomingViewHolder

class FavoriteAdapter (
private val listener: Listener,
private val list: MutableList<UpcomingViewData> = mutableListOf()
) : RecyclerView.Adapter<UpcomingViewHolder>() {

    interface Listener: UpcomingViewHolder.Listener

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): UpcomingViewHolder {
        return UpcomingViewHolder(parent, listener)
    }

    override fun onBindViewHolder(
        holder: UpcomingViewHolder,
        position: Int
    ) {
        holder.bind(list[position])
    }

    fun setItem(list: List<UpcomingViewData>) {
        this.list.clear()
        this.list.addAll(list)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = list.size
}