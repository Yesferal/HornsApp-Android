package com.yesferal.hornsapp.domain.entity.drawer

class ScreenDrawer(
    private val key: String,
    val title: TextDrawer?,
    val categories: List<CategoryDrawer>?
) {
    enum class Type {
        NEWEST,
        UPCOMING,
        FAVORITE,
        UNDETERMINED
    }

    val type: Type
        get() = when (key) {
            Type.NEWEST.name -> Type.NEWEST
            Type.UPCOMING.name -> Type.UPCOMING
            Type.FAVORITE.name -> Type.FAVORITE
            else -> Type.UNDETERMINED
        }
}
