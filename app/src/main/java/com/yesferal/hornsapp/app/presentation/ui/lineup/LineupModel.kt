/* Copyright © 2025 HornsApp. All rights reserved. */
package com.yesferal.hornsapp.app.presentation.ui.lineup

import androidx.annotation.StringRes
import com.yesferal.hornsapp.core.domain.entity.Stage

data class LineupViewState(
    val day: String? = null,
    val headers: List<String>? = null,
    val stages: List<Stage>? = null,
    val isLoading: Boolean = false,
    @StringRes val errorMessageId: Int? = null
)
