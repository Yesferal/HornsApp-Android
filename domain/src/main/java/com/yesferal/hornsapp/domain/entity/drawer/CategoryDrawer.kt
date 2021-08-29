package com.yesferal.hornsapp.domain.entity.drawer

class CategoryDrawer(val key: Type, val title: TextDrawer?) {
    enum class Type {
        ALL,
        LIVE,
        ONLINE,
        METAL,
        ROCK
    }
}
