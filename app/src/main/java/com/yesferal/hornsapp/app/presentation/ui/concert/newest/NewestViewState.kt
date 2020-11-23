package com.yesferal.hornsapp.app.presentation.ui.concert.newest

import androidx.annotation.StringRes
import com.yesferal.hornsapp.app.R
import com.yesferal.hornsapp.app.presentation.common.ViewHolderData
import com.yesferal.hornsapp.app.presentation.common.ViewState

class NewestViewState(
    val concerts: List<ViewHolderData>? = null,
    val isLoading: Boolean = false,
    @StringRes val errorMessage: Int? = null
) : ViewState()


class TextViewData(
    id: String,
    name: String?
): ViewHolderData(id, name) {
    override fun getViewType() = R.layout.item_newest_title
}


class NewestViewData(
    id: String,
    name: String?,
    val day: String?,
    val month: String?,
    val ticketingHostName: String?
): ViewHolderData(id, name) {

    interface Listener: ViewHolderData.Listener {
        fun onClick(newestViewData: NewestViewData)
    }

    override fun getViewType() = R.layout.item_newest
}