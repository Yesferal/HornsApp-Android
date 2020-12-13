package com.yesferal.hornsapp.app.presentation.ui.concert.newest

import android.view.View
import androidx.annotation.StringRes
import com.yesferal.hornsapp.app.R
import com.yesferal.hornsapp.app.presentation.common.base.Parcelable
import com.yesferal.hornsapp.app.presentation.common.custom.setUpWith
import com.yesferal.hornsapp.multitype.BaseViewHolder
import com.yesferal.hornsapp.multitype.model.ViewHolderBinding
import kotlinx.android.synthetic.main.custom_date_text_view.view.*
import kotlinx.android.synthetic.main.item_newest.view.*
import kotlinx.android.synthetic.main.item_newest_title.view.titleTextView

data class NewestViewState(
    val items: List<ViewHolderBinding>? = null,
    val isLoading: Boolean = false,
    @StringRes val errorMessage: Int? = null
)

data class TitleViewData(
    val name: String?
) : ViewHolderBinding {

    override val layout = R.layout.item_newest_title

    override fun onCreateViewHolder(
        itemView: View,
        listener: ViewHolderBinding.Listener
    ) = object : BaseViewHolder<TitleViewData>(itemView) {
        override fun bind(model: TitleViewData) {
            itemView.titleTextView.setUpWith(model.name)
        }
    }
}

data class NewestViewData(
    val id: String,
    val name: String?,
    val day: String?,
    val month: String?,
    val ticketingHostName: String?
) : ViewHolderBinding, Parcelable {

    override val layout = R.layout.item_newest

    override fun asParcelable(): Parcelable.ViewData {
        return Parcelable.ViewData(id, name)
    }

    interface Listener: ViewHolderBinding.Listener {
        fun onClick(newestViewData: NewestViewData)
    }

    override fun onCreateViewHolder(
        itemView: View,
        listener: ViewHolderBinding.Listener
    ) = object : BaseViewHolder<NewestViewData>(itemView) {
        override fun bind(model: NewestViewData) {
            itemView.containerLayout.setOnClickListener {
                (listener as Listener).onClick(model)
            }

            itemView.titleTextView.setUpWith(model.name)
            itemView.subtitleTextView.setUpWith(model.ticketingHostName)

            itemView.dayTextView.setUpWith(model.day)
            itemView.monthTextView.setUpWith(model.month)
        }
    }
}