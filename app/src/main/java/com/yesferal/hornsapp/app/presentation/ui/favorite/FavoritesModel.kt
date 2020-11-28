package com.yesferal.hornsapp.app.presentation.ui.favorite

import com.yesferal.hornsapp.app.presentation.common.base.ViewState
import com.yesferal.hornsapp.app.presentation.common.multitype.ViewHolderBinding

data class FavoritesViewState(
    val items: List<ViewHolderBinding>? = null,
    val isLoading: Boolean = false
) : ViewState