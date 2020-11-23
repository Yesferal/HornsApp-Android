package com.yesferal.hornsapp.app.presentation.ui.concert.newest

import androidx.annotation.StringRes
import com.yesferal.hornsapp.app.R
import com.yesferal.hornsapp.app.presentation.common.ParcelableViewData
import com.yesferal.hornsapp.app.presentation.common.ViewHolderData
import com.yesferal.hornsapp.app.presentation.common.ViewState

class NewestViewState(
    val concerts: List<ViewHolderData>? = null,
    val isLoading: Boolean = false,
    @StringRes val errorMessage: Int? = null
) : ViewState()


class TextViewData(
    val id: String,
    val name: String?
): ViewHolderData {
    override fun getViewType() = R.layout.item_newest_title
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

    override fun getViewType() = R.layout.item_newest
}