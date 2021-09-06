package com.yesferal.hornsapp.data.abstraction.remote

import com.yesferal.hornsapp.domain.entity.drawer.CategoryDrawer
import com.yesferal.hornsapp.domain.entity.drawer.ScreenDrawer
import kotlinx.coroutines.flow.Flow

interface DrawerRemoteDataSource {
    val screenDrawer: Flow<List<ScreenDrawer>>
    val categoryDrawer: Flow<List<CategoryDrawer>>
}
