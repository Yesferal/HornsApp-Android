package com.yesferal.hornsapp.app.presentation.ui.settings

data class SettingsState(
    val environment: Int,
    val environments: List<Pair<String, String>>
)