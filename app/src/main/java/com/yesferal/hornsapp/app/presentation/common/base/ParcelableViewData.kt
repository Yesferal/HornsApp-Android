package com.yesferal.hornsapp.app.presentation.common.base

import android.os.Parcelable
import com.yesferal.hornsapp.core.domain.navigator.NavViewData
import kotlinx.parcelize.Parcelize

@Parcelize
class ParcelableViewData(
        override val id: String,
        override val name: String?
) : Parcelable, NavViewData
