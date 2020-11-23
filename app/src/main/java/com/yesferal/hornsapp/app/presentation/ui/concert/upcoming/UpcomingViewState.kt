package com.yesferal.hornsapp.app.presentation.ui.concert.upcoming

import android.view.View
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.yesferal.hornsapp.app.R
import com.yesferal.hornsapp.app.presentation.common.*
import com.yesferal.hornsapp.app.presentation.common.ui.multitype.HornsViewHolder
import com.yesferal.hornsapp.app.presentation.common.ui.multitype.ViewHolderData
import com.yesferal.hornsapp.app.presentation.ui.concert.upcoming.adapter.ErrorViewHolder
import com.yesferal.hornsapp.app.presentation.ui.concert.upcoming.adapter.UpcomingViewHolder
import com.yesferal.hornsapp.app.presentation.ui.filters.CategoryViewData
import com.yesferal.hornsapp.app.presentation.ui.filters.FiltersViewHolder

data class UpcomingViewState(
    val items: List<ViewHolderData>? = null,
    val isLoading: Boolean = false,
    val error: ErrorViewData? = null
) : ViewState()

data class UpcomingViewData(
    val id: String,
    val name: String?,
    val image: String?,
    val day: String?,
    val month: String?,
    val year: String?,
    val time: String?,
    val genre: String?
) : ViewHolderData {

    fun asParcelable(): ParcelableViewData {
        return ParcelableViewData(
            id,
            name
        )
    }

    interface Listener: ViewHolderData.Listener {
        fun onClick(upcomingViewData: UpcomingViewData)
    }

    override fun getViewType(): Int = R.layout.item_upcoming

    @Suppress("UNCHECKED_CAST")
    override fun getViewHolder(
        itemView: View,
        listener: ViewHolderData.Listener
    ): HornsViewHolder<ViewHolderData> {
        return UpcomingViewHolder(itemView, listener) as HornsViewHolder<ViewHolderData>
    }
}

data class FiltersViewData(
    val categories: List<CategoryViewData>
) : ViewHolderData {

    interface Listener: ViewHolderData.Listener {
        fun onClick(categoryViewData: CategoryViewData)
    }

    override fun getViewType(): Int = R.layout.item_filters

    @Suppress("UNCHECKED_CAST")
    override fun getViewHolder(
        itemView: View,
        listener: ViewHolderData.Listener
    ): HornsViewHolder<ViewHolderData> {
        return FiltersViewHolder(itemView, listener) as HornsViewHolder<ViewHolderData>
    }
}

data class ErrorViewData(
    @DrawableRes val imageId: Int,
    @StringRes val errorMessage: Int
) : ViewHolderData {

    override fun getViewType(): Int = R.layout.custom_error

    @Suppress("UNCHECKED_CAST")
    override fun getViewHolder(
        itemView: View,
        listener: ViewHolderData.Listener
    ): HornsViewHolder<ViewHolderData> {
        return ErrorViewHolder(itemView) as HornsViewHolder<ViewHolderData>
    }
}