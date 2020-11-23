package com.yesferal.hornsapp.app.presentation.common.ui.custom

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.yesferal.hornsapp.app.presentation.common.ViewHolderData

abstract class HornsViewHolder<VIEW_DATA: ViewHolderData>(
    itemView: View,
    var listener: ViewHolderData.Listener? = null
) : RecyclerView.ViewHolder(itemView) {

    abstract fun bind(viewData: VIEW_DATA)
}