package com.yesferal.hornsapp.app.presentation.common.base

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class ParcelableViewData(
        val id: String,
        val name: String?
) : Parcelable
