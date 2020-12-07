package com.yesferal.hornsapp.app.presentation.common.multitype

import android.view.View
import com.yesferal.hornsapp.app.presentation.common.base.LayoutBinding

interface ViewHolderBinding: LayoutBinding {

    interface Listener

    fun onCreateViewHolder(
        itemView: View,
        listener: Listener
    ) : BaseViewHolder<out ViewHolderBinding>
}