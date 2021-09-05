package com.yesferal.hornsapp.data.abstraction.remote

import com.yesferal.hornsapp.domain.entity.drawer.AppDrawer
import kotlinx.coroutines.flow.Flow

interface DrawerRemoteDataSource {
    val appDrawer: Flow<AppDrawer>
}
