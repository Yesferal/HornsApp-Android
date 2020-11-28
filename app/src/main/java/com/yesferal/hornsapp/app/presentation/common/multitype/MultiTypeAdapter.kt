package com.yesferal.hornsapp.app.presentation.common.multitype

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import java.lang.Exception

class MultiTypeAdapter (
    private val listener: ViewHolderBinding.Listener,
    private val list: MutableList<ViewHolderBinding> = mutableListOf()
) : RecyclerView.Adapter<BaseViewHolder<ViewHolderBinding>>() {

    private val viewTypes: HashMap<Int, (
        itemView: View,
        listener: ViewHolderBinding.Listener
    ) -> BaseViewHolder<ViewHolderBinding>> = hashMapOf()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<ViewHolderBinding> {
        val itemView = LayoutInflater
            .from(parent.context)
            .inflate(viewType, parent, false)
        return viewTypes[viewType]?.invoke(itemView, listener)
            ?: throw Exception("${this::class.java} could not found viewType or layout #$viewType")
    }

    override fun getItemViewType(position: Int): Int {
        return list[position].layout
    }

    override fun onBindViewHolder(
        holder: BaseViewHolder<ViewHolderBinding>,
        position: Int
    ) {
        holder.bind(list[position])
    }

    fun setItems(list: List<ViewHolderBinding>) {
        this.list.clear()
        list.forEach {
            this.list.add(it)
            this.viewTypes[it.layout] = { view, listener ->
                it.onCreateViewHolder(view, listener)
            }
        }

        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = list.size
}