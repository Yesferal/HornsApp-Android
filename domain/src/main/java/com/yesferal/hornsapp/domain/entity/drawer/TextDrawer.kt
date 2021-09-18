package com.yesferal.hornsapp.domain.entity.drawer

import java.util.*

data class TextDrawer(private val en: String?, private val es: String?) {
    val text: String?
        get() = when (Locale.getDefault().language) {
            "en" -> en
            "es" -> es
            else -> en
        }
}
