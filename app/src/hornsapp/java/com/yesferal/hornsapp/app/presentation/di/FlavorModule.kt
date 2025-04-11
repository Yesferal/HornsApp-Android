/* Copyright © 2025 HornsApp. All rights reserved. */
package com.yesferal.hornsapp.app.presentation.di

import com.yesferal.hornsapp.hadi.container.Container
import com.yesferal.hornsapp.hadi.dependency.Factory

fun Container.registerFlavorModule() {
    this register Factory {
        FlavorDataClass("yesferal-lib")
    }
}
