package com.yesferal.hornsapp.app.presentation.concert.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yesferal.hornsapp.domain.entity.Category

class CategoryAdapter (
    private val listener: Listener,
    private var list: List<Category> = listOf(),
    private var selectedCategoryId: String = ""
) : RecyclerView.Adapter<CategoryViewHolder>() {

    interface Listener {
        fun onClick(category: Category)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CategoryViewHolder {
        return CategoryViewHolder(parent, listener)
    }

    fun setItems(
        list: List<Category>
    ) {
        this.list = list
    }

    fun setCategoryId(
        selectedCategoryId: String
    ) {
        this.selectedCategoryId = selectedCategoryId
        notifyDataSetChanged()
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(
        holder: CategoryViewHolder,
        position: Int
    ) {
        holder.bind(selectedCategoryId, list[position])
    }
}