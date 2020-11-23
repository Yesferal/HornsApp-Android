package com.yesferal.hornsapp.app.presentation.common.ui.custom

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yesferal.hornsapp.app.presentation.common.ViewHolderData
import java.lang.Exception

class HornsAdapter (
    private val viewTypes: Map<Int, (
        itemView: View,
        listener: ViewHolderData.Listener
    ) -> HornsViewHolder<ViewHolderData>>,
    private val listener: ViewHolderData.Listener,
    private val list: MutableList<ViewHolderData> = mutableListOf()
) : RecyclerView.Adapter<HornsViewHolder<ViewHolderData>>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HornsViewHolder<ViewHolderData> {
        val itemView = LayoutInflater
            .from(parent.context)
            .inflate(viewType, parent, false)
        return viewTypes[viewType]?.invoke(itemView, listener)
            ?: throw Exception("${this::class.java} could not found viewType [$viewType] or layout")
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