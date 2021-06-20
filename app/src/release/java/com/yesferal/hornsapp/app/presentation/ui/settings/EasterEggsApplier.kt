package com.yesferal.hornsapp.app.presentation.ui.settings

import android.widget.Toast
import androidx.fragment.app.Fragment
import com.yesferal.hornsapp.app.R

interface EasterEggsApplier {
    fun versionSuffix() = "R"

    fun Fragment.onAppImageViewClick() {
        Toast.makeText(context, getString(R.string.app_name), Toast.LENGTH_SHORT)
            .show()
    }
}