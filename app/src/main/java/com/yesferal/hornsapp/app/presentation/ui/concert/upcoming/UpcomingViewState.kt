package com.yesferal.hornsapp.app.presentation.ui.concert.upcoming

import android.view.View
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.yesferal.hornsapp.app.R
import com.yesferal.hornsapp.app.presentation.common.base.ParcelableViewData
import com.yesferal.hornsapp.app.presentation.common.base.ViewState
import com.yesferal.hornsapp.app.presentation.common.multitype.BaseViewHolder
import com.yesferal.hornsapp.app.presentation.common.multitype.ViewHolderBinding
import com.yesferal.hornsapp.app.presentation.ui.concert.upcoming.adapter.ErrorViewHolder
import com.yesferal.hornsapp.app.presentation.ui.concert.upcoming.adapter.UpcomingViewHolder
import com.yesferal.hornsapp.app.presentation.ui.filters.CategoryViewData
import com.yesferal.hornsapp.app.presentation.ui.filters.FiltersViewHolder

data class UpcomingViewState(
    val items: List<ViewHolderBinding>? = null,
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
) : ViewHolderBinding {

    fun asParcelable(): ParcelableViewData {
        return ParcelableViewData(
            id,
            name
        )
    }

    interface Listener: ViewHolderBinding.Listener {
        fun onClick(upcomingViewData: UpcomingViewData)
    }

    override val layout = R.layout.item_upcoming

    @Suppress("UNCHECKED_CAST")
    override fun onCreateViewHolder(
        itemView: View,
        listener: ViewHolderBinding.Listener
    ): BaseViewHolder<ViewHolderBinding> {
        return UpcomingViewHolder(itemView, listener) as BaseViewHolder<ViewHolderBinding>
    }
}

data class FiltersViewData(
    val categories: List<CategoryViewData>
) : ViewHolderBinding {

    interface Listener: ViewHolderBinding.Listener {
        fun onClick(categoryViewData: CategoryViewData)
    }

    override val layout = R.layout.item_filters

    @Suppress("UNCHECKED_CAST")
    override fun onCreateViewHolder(
        itemView: View,
        listener: ViewHolderBinding.Listener
    ): BaseViewHolder<ViewHolderBinding> {
        return FiltersViewHolder(itemView, listener) as BaseViewHolder<ViewHolderBinding>
    }
}

data class ErrorViewData(
    @DrawableRes val imageId: Int,
    @StringRes val errorMessage: Int
) : ViewHolderBinding {

    override val layout = R.layout.custom_error

    @Suppress("UNCHECKED_CAST")
    override fun onCreateViewHolder(
        itemView: View,
        listener: ViewHolderBinding.Listener
    ): BaseViewHolder<ViewHolderBinding> {
        return ErrorViewHolder(itemView) as BaseViewHolder<ViewHolderBinding>
    }
}