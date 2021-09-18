package com.yesferal.hornsapp.data.abstraction.storage

import com.yesferal.hornsapp.domain.entity.drawer.AppDrawer

interface DrawerStorageDataSource {
    fun getAppDrawer(): AppDrawer
    fun updateAppDrawer(appDrawer: AppDrawer)
}
