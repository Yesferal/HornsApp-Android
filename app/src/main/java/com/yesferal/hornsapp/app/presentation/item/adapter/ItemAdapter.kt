package com.yesferal.hornsapp.app.presentation.item.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class ItemAdapter (
    private val listener: Listener
) : RecyclerView.Adapter<ItemViewHolder>() {
    private var list: List<HornsAppItem> = listOf()

    interface Listener {
        fun onClick(item: HornsAppItem)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ItemViewHolder {
        return ItemViewHolder(parent, listener)
    }

    fun setItem(list: List<HornsAppItem>?) {
        this.list = list?: listOf()
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(
        holder: ItemViewHolder,
        position: Int
    ) {
        holder.bind(list[position])
    }
}