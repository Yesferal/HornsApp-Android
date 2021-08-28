package com.yesferal.hornsapp.data.abstraction.features

import com.yesferal.hornsapp.domain.entity.drawer.HomeDrawer

interface DrawerDataSource {
    fun getHomeDrawer(): HomeDrawer
}
