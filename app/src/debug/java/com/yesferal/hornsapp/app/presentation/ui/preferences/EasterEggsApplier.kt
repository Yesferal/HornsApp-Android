package com.yesferal.hornsapp.app.presentation.ui.preferences

import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.yesferal.hornsapp.app.presentation.ui.home.HomeFragmentDirections

interface EasterEggsApplier {
    fun versionSuffix() = "DEV"

    fun Fragment.onAppImageViewClick() {
        findNavController().navigate(HomeFragmentDirections.actionHomeToPreferences())
    }
}