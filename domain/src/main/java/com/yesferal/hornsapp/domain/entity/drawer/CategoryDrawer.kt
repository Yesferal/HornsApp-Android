package com.yesferal.hornsapp.domain.entity.drawer

class CategoryDrawer(val type: Type, val title: TextDrawer?) {
    enum class Type {
        ALL,
        LIVE,
        ONLINE,
        METAL,
        ROCK
    }
}
