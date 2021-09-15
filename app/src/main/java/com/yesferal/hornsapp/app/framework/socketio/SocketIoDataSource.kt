package com.yesferal.hornsapp.app.framework.socketio

import com.google.gson.Gson
import com.yesferal.hornsapp.data.abstraction.remote.DrawerRemoteDataSource
import com.yesferal.hornsapp.data.abstraction.storage.DrawerStorageDataSource
import com.yesferal.hornsapp.domain.abstraction.Logger
import com.yesferal.hornsapp.domain.entity.drawer.AppDrawer
import com.yesferal.hornsapp.domain.entity.drawer.CategoryDrawer
import com.yesferal.hornsapp.domain.entity.drawer.ScreenDrawer
import io.socket.client.IO
import io.socket.client.Socket
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.net.URI

class SocketIoDataSource(
    private val gson: Gson,
    private val logger: Logger,
    baseUrl: String,
    drawerStorageDataSource: DrawerStorageDataSource
) : DrawerRemoteDataSource {

    private lateinit var socket: Socket

    private val _homeDrawer =
        MutableStateFlow(drawerStorageDataSource.getAppDrawer().screens ?: listOf())
    override val homeDrawer: StateFlow<List<ScreenDrawer>>
        get() = _homeDrawer

    private val _newestDrawer =
        MutableStateFlow(drawerStorageDataSource.getAppDrawer().newest ?: listOf())
    override val newestDrawer: StateFlow<List<ScreenDrawer>>
        get() = _newestDrawer

    private val _categoryDrawer =
        MutableStateFlow(drawerStorageDataSource.getAppDrawer().categories ?: listOf())
    override val categoryDrawer: StateFlow<List<CategoryDrawer>>
        get() = _categoryDrawer

    init {
        try {
            val options = IO.Options()
            socket = IO.socket(URI(baseUrl), options)
            logger.d("Success: " + socket.id())
        } catch (e: Exception) {
            logger.e("Fail: ${e.printStackTrace()}")
        }

        socket.connect()

        socket.on("connect_error") {
            logger.e("Socket On (connect_error): ${it[0]}")
        }

        socket.on("updateDrawer") {
            logger.d("Socket On (updateDrawer): ${it[0]}")
            lateinit var appDrawer: AppDrawer
            try {
                appDrawer = gson.fromJson(it[0].toString(), AppDrawer::class.java)
                drawerStorageDataSource.updateAppDrawer(appDrawer)
                _homeDrawer.value = appDrawer.screens ?: listOf()
                _newestDrawer.value = appDrawer.newest ?: listOf()
                _categoryDrawer.value = appDrawer.categories ?: listOf()
            } catch (e: java.lang.Exception) {
                logger.e("Socket On (updateDrawer): ${e.message.orEmpty()}")
            }
        }
    }
}
