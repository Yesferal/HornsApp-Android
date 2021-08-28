package com.yesferal.hornsapp.data.abstraction.features

import com.yesferal.hornsapp.domain.entity.drawer.AppDrawer

interface DrawerDataSource {
    fun getAppDrawer(): AppDrawer
}
