package com.yesferal.hornsapp.app.presentation.concert.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yesferal.hornsapp.domain.entity.Category

class CategoryAdapter (
    private val listener: Listener
) : RecyclerView.Adapter<CategoryViewHolder>() {
    private var list: List<Category> = listOf()

    interface Listener {
        fun openCategory(id: String)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        return CategoryViewHolder(parent, listener)
    }

    fun setItem(list: List<Category>) {
        this.list = list
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bind(list[position])
    }
}