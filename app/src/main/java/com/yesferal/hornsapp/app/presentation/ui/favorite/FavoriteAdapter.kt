package com.yesferal.hornsapp.app.presentation.ui.favorite

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yesferal.hornsapp.app.presentation.ui.concert.search.ConcertViewData
import com.yesferal.hornsapp.app.presentation.ui.concert.search.adapter.ConcertViewHolder

class FavoriteAdapter (
private val listener: Listener,
private val list: MutableList<ConcertViewData> = mutableListOf()
) : RecyclerView.Adapter<ConcertViewHolder>() {

    interface Listener: ConcertViewHolder.Listener

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ConcertViewHolder {
        return ConcertViewHolder(parent, listener)
    }

    override fun onBindViewHolder(
        holder: ConcertViewHolder,
        position: Int
    ) {
        holder.bind(list[position])
    }

    fun setItem(list: List<ConcertViewData>) {
        this.list.clear()
        this.list.addAll(list)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = list.size
}