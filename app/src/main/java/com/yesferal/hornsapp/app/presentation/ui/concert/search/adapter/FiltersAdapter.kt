package com.yesferal.hornsapp.app.presentation.ui.concert.search.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class FiltersAdapter(
    private val listener: FilterViewHolder.Listener,
    private val list: MutableList<CategoryViewData> = mutableListOf()
) : RecyclerView.Adapter<FilterViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FilterViewHolder {
        return FilterViewHolder(parent, listener)
    }

    fun setItems(list: List<CategoryViewData>) {
        this.list.clear()
        this.list.addAll(list)

        notifyDataSetChanged()
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(
        holder: FilterViewHolder,
        position: Int
    ) {
        holder.bind(list[position])
    }
}