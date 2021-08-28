package com.yesferal.hornsapp.domain.entity.drawer

class ScreenDrawer(
    val type: Type,
    val title: TextDrawer?,
    val categories: List<CategoryDrawer>?
) {
    enum class Type {
        NEWEST,
        UPCOMING,
        FAVORITE
    }
}
