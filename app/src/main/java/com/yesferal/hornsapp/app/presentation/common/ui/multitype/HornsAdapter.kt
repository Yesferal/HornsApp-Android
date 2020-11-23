package com.yesferal.hornsapp.app.presentation.common.ui.multitype

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import java.lang.Exception

class HornsAdapter (
    private val listener: ViewHolderData.Listener,
    private val list: MutableList<ViewHolderData> = mutableListOf()
) : RecyclerView.Adapter<HornsViewHolder<ViewHolderData>>() {

    private val viewTypes: HashMap<Int, (
        itemView: View,
        listener: ViewHolderData.Listener
    ) -> HornsViewHolder<ViewHolderData>> = hashMapOf()

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
        list.forEach {
            this.list.add(it)
            this.viewTypes[it.getViewType()] = { view, listener -> it.getViewHolder(view, listener) }
        }

        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = list.size
}