package com.yesferal.hornsapp.app.framework.socketio

import com.google.gson.Gson
import com.yesferal.hornsapp.data.abstraction.remote.DrawerRemoteDataSource
import com.yesferal.hornsapp.domain.abstraction.Logger
import com.yesferal.hornsapp.domain.entity.drawer.AppDrawer
import io.socket.client.IO
import io.socket.client.Socket
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.net.URI

class SocketIoDataSource(
    private val gson: Gson,
    private val logger: Logger,
    baseUrl: String,
    defaultAppDrawer: AppDrawer
) : DrawerRemoteDataSource {

    private lateinit var socket: Socket

    private val _appDrawer = MutableStateFlow(defaultAppDrawer)
    override val appDrawer: StateFlow<AppDrawer>
        get() = _appDrawer

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
                _appDrawer.value = appDrawer
            } catch (e: java.lang.Exception) {
                logger.e("Socket On (updateDrawer): ${e.message.orEmpty()}")
            }
        }
    }
}
