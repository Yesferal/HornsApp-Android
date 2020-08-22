package com.yesferal.hornsapp.app.presentation.common.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class BaseAdapter (
    private val listener: Listener
) : RecyclerView.Adapter<BaseViewHolder>() {
    private var list: List<HornsAppItem> = listOf()

    interface Listener {
        fun onClick(item: HornsAppItem)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder {
        return BaseViewHolder(parent, listener)
    }

    fun setItem(list: List<HornsAppItem>?) {
        this.list = list?: listOf()
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(
        holder: BaseViewHolder,
        position: Int
    ) {
        holder.bind(list[position])
    }
}