/* Copyright © 2023 HornsApp. All rights reserved. */
package com.yesferal.hornsapp.app.presentation.ui.settings

import com.yesferal.hornsapp.app.presentation.common.base.BaseFragment
import com.yesferal.hornsapp.core.domain.navigator.Navigator
import com.yesferal.hornsapp.core.domain.navigator.ScreenType

interface EasterEggsApplier {
    fun versionSuffix() = "DEV"

    fun BaseFragment.onAppImageViewClick() {
        Navigator.Builder()
            .to(ScreenType.SETTING)
            .build()
            .navigateTo()
    }
}
