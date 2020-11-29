package com.yesferal.hornsapp.app.presentation.ui.filters

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class CategoriesAdapter(
    private val listener: CategoryViewHolder.Listener,
    private val list: MutableList<CategoryViewData> = mutableListOf()
) : RecyclerView.Adapter<CategoryViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CategoryViewHolder {
        return CategoryViewHolder(parent, listener)
    }

    fun setItems(list: List<CategoryViewData>) {
        this.list.clear()
        this.list.addAll(list)

        notifyDataSetChanged()
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(
        holder: CategoryViewHolder,
        position: Int
    ) {
        holder.bind(list[position])
    }
}