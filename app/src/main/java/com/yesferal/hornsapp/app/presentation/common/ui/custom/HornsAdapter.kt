package com.yesferal.hornsapp.app.presentation.common.ui.custom

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yesferal.hornsapp.app.presentation.common.ViewHolderData

class HornsAdapter (
    private val listener: ViewHolderData.Listener,
    private val list: MutableList<ViewHolderData> = mutableListOf()
) : RecyclerView.Adapter<HornsViewHolder<ViewHolderData>>() {


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HornsViewHolder<ViewHolderData> {
        return HornsViewHolder.onCreateViewHolder(parent, viewType, listener)
    }

    override fun getItemViewType(position: Int): Int {
        return list[position].getViewType()
    }

    override fun onBindViewHolder(
        holder: HornsViewHolder<ViewHolderData>,
        position: Int
    ) {
        holder.bind(list[position])
    }

    fun setItems(list: List<ViewHolderData>) {
        this.list.clear()
        this.list.addAll(list)

        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = list.size
}