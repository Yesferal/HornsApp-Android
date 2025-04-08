/* Copyright © 2025 HornsApp. All rights reserved. */
package com.yesferal.hornsapp.app.presentation.ui.lineup

import android.view.View
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.yesferal.hornsapp.app.R
import com.yesferal.hornsapp.delegate.abstraction.Delegate
import com.yesferal.hornsapp.delegate.viewholder.DelegateViewHolder
import com.yesferal.hornsapp.delegate.DelegateAdapter
import com.yesferal.hornsapp.delegate.abstraction.DelegateListener
import com.yesferal.hornsapp.delegate.delegate.DividerDelegate
import com.yesferal.hornsapp.delegate.delegate.InteractiveDelegate

class ColumnDelegate private constructor(
    private val items: List<Delegate>,
    private val horizontalMargin: Int,
    @ColorRes private val background: Int?,
    private val elevation: Float
) : InteractiveDelegate<DelegateListener> {

    override fun onCreateViewHolder(
        itemView: View,
        listener: DelegateListener
    ): DelegateViewHolder {
        return DelegateViewHolder(itemView, listener)
    }

    override val layout: Int
        get() = R.layout.item_column_delegate

    override fun onBindViewDelegate(view: View, listener: DelegateListener) {
        val adapter = DelegateAdapter.Builder()
            .addItem(DividerDelegate(horizontalMargin, 0))
            .addItems(items)
            .addItem(DividerDelegate(horizontalMargin, 0))
            .setListener(listener)
            .build()
        val linearLayoutManager =
            LinearLayoutManager(view.context, LinearLayoutManager.VERTICAL, false)
        view.findViewById<RecyclerView>(R.id.recyclerView)?.let { recyclerView ->
            recyclerView.adapter = adapter
            recyclerView.layoutManager = linearLayoutManager
        }
        background?.let {
            view.setBackgroundColor(ContextCompat.getColor(view.context, it))
        }
        view.elevation = elevation
    }

    class Builder {
        private var items: MutableList<Delegate> = mutableListOf()
        private var horizontalMargin: Int = 0
        @ColorRes private var background: Int? = null
        private var elevation: Float = 0F

        fun addItems(items: List<Delegate>) = apply {
            this.items.addAll(items)
        }

        fun addHorizontalMargin(horizontalMargin: Int) = apply {
            this.horizontalMargin = horizontalMargin
        }

        fun addBackground(background: Int) = apply {
            this.background = background
        }

        fun addElevation(elevation: Float) = apply {
            this.elevation = elevation
        }

        fun build() = ColumnDelegate(items, horizontalMargin, background, elevation)
    }
}
