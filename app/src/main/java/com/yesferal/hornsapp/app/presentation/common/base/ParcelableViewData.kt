/* Copyright © 2023 HornsApp. All rights reserved. */
package com.yesferal.hornsapp.app.presentation.common.base

import android.os.Parcelable
import com.yesferal.hornsapp.app.framework.navigator.FragmentNavigator
import com.yesferal.hornsapp.core.domain.navigator.NavViewData
import com.yesferal.hornsapp.core.domain.navigator.Parameters
import kotlinx.parcelize.Parcelize

@Parcelize
class ParcelableViewData(
        val id: String,
        val name: String?
) : Parcelable, NavViewData {
        override fun toMap(): Parameters {
                return Parameters().apply {
                        put(FragmentNavigator.PARAM_PARCELABLE_VIEW_DATA, this@ParcelableViewData)
                }
        }
}
