package com.yesferal.hornsapp.app.presentation.ui.concert.newest

import android.view.View
import androidx.annotation.StringRes
import com.yesferal.hornsapp.app.R
import com.yesferal.hornsapp.app.presentation.common.base.ParcelableViewData
import com.yesferal.hornsapp.app.presentation.common.multitype.ViewHolderBinding
import com.yesferal.hornsapp.app.presentation.common.base.ViewState
import com.yesferal.hornsapp.app.presentation.common.multitype.BaseViewHolder
import com.yesferal.hornsapp.app.presentation.ui.concert.newest.adapter.NewestTitleViewHolder
import com.yesferal.hornsapp.app.presentation.ui.concert.newest.adapter.NewestViewHolder

data class NewestViewState(
    val items: List<ViewHolderBinding>? = null,
    val isLoading: Boolean = false,
    @StringRes val errorMessage: Int? = null
) : ViewState

data class TitleViewData(
    val id: String,
    val name: String?
) : ViewHolderBinding {
    override val layout = R.layout.item_newest_title

    @Suppress("UNCHECKED_CAST")
    override fun onCreateViewHolder(
        itemView: View,
        listener: ViewHolderBinding.Listener
    ): BaseViewHolder<ViewHolderBinding> {
        return NewestTitleViewHolder(itemView) as BaseViewHolder<ViewHolderBinding>
    }
}


data class NewestViewData(
    val id: String,
    val name: String?,
    val day: String?,
    val month: String?,
    val ticketingHostName: String?
) : ViewHolderBinding {

    fun asParcelable(): ParcelableViewData {
        return ParcelableViewData(
            id,
            name
        )
    }

    interface Listener: ViewHolderBinding.Listener {
        fun onClick(newestViewData: NewestViewData)
    }

    override val layout = R.layout.item_newest

    @Suppress("UNCHECKED_CAST")
    override fun onCreateViewHolder(
        itemView: View,
        listener: ViewHolderBinding.Listener
    ): BaseViewHolder<ViewHolderBinding> {
        return NewestViewHolder(itemView, listener) as BaseViewHolder<ViewHolderBinding>
    }
}