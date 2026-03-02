/* Copyright © 2025 HornsApp. All rights reserved. */
package com.yesferal.hornsapp.app.presentation.di

data class SettingFlavorDataClass (
    val cFileName: String,
    val eventsPath: String,
    val appId: String,
) {
    companion object {
        val PLATFORM = "android"
    }
}
