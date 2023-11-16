/* Copyright © 2023 HornsApp. All rights reserved. */
package com.yesferal.hornsapp.app.presentation.di

import com.yesferal.hornsapp.app.framework.adMob.AdUnitIds
import com.yesferal.hornsapp.app.framework.adMob.DebugAdUnitIds
import com.yesferal.hornsapp.hadi.container.Container
import com.yesferal.hornsapp.hadi.dependency.Factory

fun Container.registerBuildVariantModule() {
    this register Factory<AdUnitIds> {
        DebugAdUnitIds()
    }
}
