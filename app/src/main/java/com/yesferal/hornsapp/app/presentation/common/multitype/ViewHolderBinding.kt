package com.yesferal.hornsapp.app.presentation.common.multitype

import android.view.View
import androidx.annotation.LayoutRes

interface ViewHolderBinding {

    interface Listener

    @get:LayoutRes val layout: Int

    fun onCreateViewHolder(itemView: View, listener: Listener): BaseViewHolder<ViewHolderBinding>
}