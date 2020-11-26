package com.yesferal.hornsapp.app.presentation.ui.concert.newest

import android.view.View
import androidx.annotation.StringRes
import com.yesferal.hornsapp.app.R
import com.yesferal.hornsapp.app.presentation.common.ParcelableViewData
import com.yesferal.hornsapp.app.presentation.common.ui.multitype.ViewHolderData
import com.yesferal.hornsapp.app.presentation.common.ViewState
import com.yesferal.hornsapp.app.presentation.common.ui.multitype.HornsViewHolder
import com.yesferal.hornsapp.app.presentation.ui.concert.newest.adapter.NewestTitleViewHolder
import com.yesferal.hornsapp.app.presentation.ui.concert.newest.adapter.NewestViewHolder

class NewestViewState(
    val concerts: List<ViewHolderData>? = null,
    val isLoading: Boolean = false,
    @StringRes val errorMessage: Int? = null
) : ViewState()


class NewestTitleViewData(
    val id: String,
    val name: String?
): ViewHolderData {
    override val layout = R.layout.item_newest_title

    @Suppress("UNCHECKED_CAST")
    override fun onCreateViewHolder(
        itemView: View,
        listener: ViewHolderData.Listener
    ): HornsViewHolder<ViewHolderData> {
        return NewestTitleViewHolder(itemView) as HornsViewHolder<ViewHolderData>
    }
}


class NewestViewData(
    val id: String,
    val name: String?,
    val day: String?,
    val month: String?,
    val ticketingHostName: String?
): ViewHolderData {

    fun asParcelable(): ParcelableViewData {
        return ParcelableViewData(
            id,
            name
        )
    }

    interface Listener: ViewHolderData.Listener {
        fun onClick(newestViewData: NewestViewData)
    }

    override val layout = R.layout.item_newest

    @Suppress("UNCHECKED_CAST")
    override fun onCreateViewHolder(
        itemView: View,
        listener: ViewHolderData.Listener
    ): HornsViewHolder<ViewHolderData> {
        return NewestViewHolder(itemView, listener) as HornsViewHolder<ViewHolderData>
    }
}