package com.yesferal.hornsapp.data.abstraction.features

import com.yesferal.hornsapp.domain.entity.drawer.AppDrawer

interface UpdateDrawerDataSource {
    fun getAppDrawer(): AppDrawer?
    fun updateAppDrawer(appDrawer: AppDrawer)
}