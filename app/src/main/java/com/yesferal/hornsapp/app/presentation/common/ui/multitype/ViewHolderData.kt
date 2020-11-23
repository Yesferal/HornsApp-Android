package com.yesferal.hornsapp.app.presentation.common.ui.multitype

import android.view.View

interface ViewHolderData {

    interface Listener

    fun getViewType(): Int
    fun getViewHolder(itemView: View, listener: Listener): HornsViewHolder<ViewHolderData>
}