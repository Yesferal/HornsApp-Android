package com.yesferal.hornsapp.app.presentation.ui.concert.newest

import android.view.View
import androidx.annotation.StringRes
import com.yesferal.hornsapp.app.R
import com.yesferal.hornsapp.app.presentation.common.base.ParcelableViewData
import com.yesferal.hornsapp.app.presentation.common.multitype.ViewHolderBinding
import com.yesferal.hornsapp.app.presentation.common.base.ViewState
import com.yesferal.hornsapp.app.presentation.common.custom.setUpWith
import com.yesferal.hornsapp.app.presentation.common.multitype.BaseViewHolder
import kotlinx.android.synthetic.main.custom_date_text_view.view.*
import kotlinx.android.synthetic.main.item_newest.view.*
import kotlinx.android.synthetic.main.item_newest_title.view.titleTextView

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

    override fun onCreateViewHolder(
        itemView: View,
        listener: ViewHolderBinding.Listener
    ) = object : BaseViewHolder<TitleViewData>(itemView) {
        override fun bind(viewData: TitleViewData) {
            itemView.titleTextView.setUpWith(viewData.name)
        }
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

    override fun onCreateViewHolder(
        itemView: View,
        listener: ViewHolderBinding.Listener
    ) = object : BaseViewHolder<NewestViewData>(itemView) {
        override fun bind(viewData: NewestViewData) {
            itemView.containerLayout.setOnClickListener {
                (listener as Listener).onClick(viewData)
            }

            itemView.titleTextView.setUpWith(viewData.name)
            itemView.subtitleTextView.setUpWith(viewData.ticketingHostName)

            itemView.dayTextView.setUpWith(viewData.day)
            itemView.monthTextView.setUpWith(viewData.month)
        }
    }
}