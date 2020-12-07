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

    fun setItems(newList: List<ViewHolderBinding>) {
        list.clear()
        newList.forEach {
            list.add(it)
            viewTypes[it.layout] = { view, listener ->
                @Suppress("UNCHECKED_CAST")
                it.onCreateViewHolder(view, listener) as BaseViewHolder<ViewHolderBinding>
            }
        }

        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = list.size
}