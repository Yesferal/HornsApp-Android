/* Copyright © 2025 HornsApp. All rights reserved. */
package com.yesferal.hornsapp.app.presentation.di

import com.yesferal.hornsapp.app.framework.adMob.AdUnitIds
import com.yesferal.hornsapp.app.framework.adMob.NativeAdUnitIds
import com.yesferal.hornsapp.hadi.container.Container
import com.yesferal.hornsapp.hadi.dependency.Factory

fun Container.registerBuildTypeModule() {
    this register Factory<AdUnitIds> {
        NativeAdUnitIds(resolve())
    }
}
