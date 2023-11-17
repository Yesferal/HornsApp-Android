/* Copyright © 2023 HornsApp. All rights reserved. */
package com.yesferal.hornsapp.app.presentation.di

import com.yesferal.hornsapp.app.framework.adMob.AdUnitIds
import com.yesferal.hornsapp.app.framework.adMob.NativeAdUnitIds
import com.yesferal.hornsapp.app.framework.adMob.ReleaseAdUnitIds
import com.yesferal.hornsapp.hadi.container.Container
import com.yesferal.hornsapp.hadi.dependency.Factory

fun Container.registerBuildVariantModule() {
    this register Factory<AdUnitIds> {
        ReleaseAdUnitIds(nativeAdUnitIds = resolve())
    }
    this register Factory {
        NativeAdUnitIds()
    }
}
