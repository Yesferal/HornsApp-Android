package com.yesferal.hornsapp.domain.entity.drawer

data class CategoryDrawer(val key: String?, val title: TextDrawer?) {
    enum class Type {
        ALL,
        METAL,
        ROCK
    }
}
