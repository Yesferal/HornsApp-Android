package com.yesferal.hornsapp.app.presentation.common.ui.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yesferal.hornsapp.app.presentation.common.entity.Item

class ItemAdapter (
    private val listener: Listener
) : RecyclerView.Adapter<ItemViewHolder>() {
    private var list: List<Item> = listOf()

    interface Listener {
        fun onClick(item: Item)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ItemViewHolder {
        return ItemViewHolder(parent, listener)
    }

    fun setItem(list: List<Item>?) {
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