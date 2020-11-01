package com.yesferal.hornsapp.app.presentation.ui.concert.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yesferal.hornsapp.app.presentation.common.ViewData
import com.yesferal.hornsapp.app.presentation.ui.concert.newest.ConcertViewData

class ConcertAdapter (
    private val listener: Listener,
    private val list: MutableList<ConcertViewData> = mutableListOf()
) : RecyclerView.Adapter<ConcertViewHolder>() {

    interface Listener {
        fun onConcertClick(concert: ViewData)
    }

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