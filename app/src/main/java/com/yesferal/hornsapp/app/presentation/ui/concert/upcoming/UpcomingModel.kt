package com.yesferal.hornsapp.app.presentation.ui.concert.upcoming

import android.view.View
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.yesferal.hornsapp.app.R
import com.yesferal.hornsapp.app.presentation.common.base.Parcelable
import com.yesferal.hornsapp.app.presentation.common.custom.load
import com.yesferal.hornsapp.app.presentation.common.custom.setAllCornersRounded
import com.yesferal.hornsapp.app.presentation.common.custom.setUpWith
import com.yesferal.hornsapp.app.presentation.common.multitype.BaseViewHolder
import com.yesferal.hornsapp.app.presentation.common.multitype.ViewHolderBinding
import com.yesferal.hornsapp.app.presentation.ui.filters.CategoryViewData
import com.yesferal.hornsapp.app.presentation.ui.filters.FiltersViewHolder
import kotlinx.android.synthetic.main.custom_date_text_view.view.*
import kotlinx.android.synthetic.main.custom_error.view.*
import kotlinx.android.synthetic.main.item_upcoming.view.*

data class UpcomingViewState(
    val items: List<ViewHolderBinding>? = null,
    val isLoading: Boolean = false
)

data class UpcomingViewData(
    val id: String,
    val name: String?,
    val image: String?,
    val day: String?,
    val month: String?,
    val year: String?,
    val time: String?,
    val genre: String?
) : ViewHolderBinding, Parcelable {

    override fun asParcelable(): Parcelable.ViewData {
        return Parcelable.ViewData(id, name)
    }

    interface Listener: ViewHolderBinding.Listener {
        fun onClick(upcomingViewData: UpcomingViewData)
    }

    override val layout = R.layout.item_upcoming

    override fun onCreateViewHolder(
        itemView: View,
        listener: ViewHolderBinding.Listener
    ) = object : BaseViewHolder<UpcomingViewData>(itemView) {
        override fun bind(viewData: UpcomingViewData) {
            viewData.year?.let {
                itemView.tagTextView.setUpWith("#$it")
            }
            itemView.titleTextView.setUpWith(viewData.name)
            itemView.dayTextView.setUpWith(viewData.day)
            itemView.monthTextView.setUpWith(viewData.month)
            itemView.timeTextView.setUpWith(viewData.time)
            itemView.genreTextView.setUpWith(viewData.genre)

            itemView.concertImageView.setAllCornersRounded()
            itemView.concertImageView.load(viewData.image)

            itemView.containerLayout.setOnClickListener {
                (listener as Listener).onClick(viewData)
            }
        }
    }
}

data class FiltersViewData(
    val categories: List<CategoryViewData>
) : ViewHolderBinding {

    interface Listener: ViewHolderBinding.Listener {
        fun onClick(categoryViewData: CategoryViewData)
    }

    override val layout = R.layout.item_filters

    override fun onCreateViewHolder(
        itemView: View,
        listener: ViewHolderBinding.Listener
    ) = FiltersViewHolder(itemView, listener as Listener) as BaseViewHolder<FiltersViewData>
}

data class ErrorViewData(
    @DrawableRes val imageId: Int,
    @StringRes val errorMessage: Int
) : ViewHolderBinding {

    override val layout = R.layout.custom_error

    override fun onCreateViewHolder(
        itemView: View,
        listener: ViewHolderBinding.Listener
    ) = object : BaseViewHolder<ErrorViewData>(itemView) {
        override fun bind(viewData: ErrorViewData) {
            itemView.errorTextView.let {
                it.setUpWith(it.context.getString(viewData.errorMessage))
            }
        }
    }
}