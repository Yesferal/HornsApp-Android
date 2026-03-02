/* Copyright © 2023 HornsApp. All rights reserved. */
package com.yesferal.hornsapp.app.framework.socketio

import com.google.gson.Gson
import com.yesferal.hornsapp.app.BuildConfig
import com.yesferal.hornsapp.app.framework.packageinfo.PackageInfoDataSource
import com.yesferal.hornsapp.core.data.abstraction.remote.RenderRemoteDataSource
import com.yesferal.hornsapp.core.data.abstraction.storage.RenderStorageDataSource
import com.yesferal.hornsapp.core.domain.abstraction.Logger
import com.yesferal.hornsapp.core.domain.entity.render.AppRender
import com.yesferal.hornsapp.core.domain.entity.render.CategoryRender
import com.yesferal.hornsapp.core.domain.entity.render.ScreenRender
import io.socket.client.IO
import io.socket.client.Socket
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.lang.StringBuilder
import java.net.URI

class SocketIoDataSource(
    private val gson: Gson,
    private val logger: Logger,
    baseUrl: String,
    drawerStorageDataSource: RenderStorageDataSource,
    packageInfoDataSource: PackageInfoDataSource
) : RenderRemoteDataSource {
    private val APP_VERSION = "appVersion"
    private val PLATFORM = "platform"
    private val APP_ID = "appId"

    private lateinit var socket: Socket

    private val _homeRender =
        MutableStateFlow(drawerStorageDataSource.getAppRender()?.screens ?: listOf())
    override val homeRender: StateFlow<List<ScreenRender>>
        get() = _homeRender

    private val _categoryRender =
        MutableStateFlow(drawerStorageDataSource.getAppRender()?.categories ?: listOf())
    override val categoryRender: StateFlow<List<CategoryRender>>
        get() = _categoryRender

    init {
        try {
            val versionCode = packageInfoDataSource.getVersionCode()
            val options = IO.Options()
            options.query = StringBuilder()
                .append(APP_VERSION)
                .append("=")
                .append(versionCode)
                .append("&")
                .append(PLATFORM)
                .append("=")
                .append("android")
                .append("&")
                .append(APP_ID)
                .append("=")
                .append(BuildConfig.APPLICATION_ID)
                .toString()
            socket = IO.socket(URI(baseUrl), options)
            logger.d("SocketIoDataSource: Success: URI(baseUrl): " + URI(baseUrl))
            logger.d("SocketIoDataSource: Success: " + socket.id())
        } catch (e: Exception) {
            logger.e("SocketIoDataSource: Fail: ${e.printStackTrace()}")
        }

        socket.connect()

        socket.on("connect_error") {
            logger.e("SocketIoDataSource: Socket On (connect_error): ${it[0]}")
        }

        socket.on("updateAppRender") {
            lateinit var appDrawer: AppRender
            try {
                logger.d("SocketIoDataSource: Socket On (updateAppRender): ${it[0]}")
                appDrawer = gson.fromJson(it[0].toString(), AppRender::class.java)

                // TODO: Validate docVersion before we save the AppDrawer doc
                drawerStorageDataSource.updateAppRender(appDrawer)
                // FIXME: We are updating the screen all the time
                // FIXME: We just validate in the updateAppRender
                _homeRender.value = appDrawer.screens ?: listOf()
                _categoryRender.value = appDrawer.categories ?: listOf()
            } catch (e: java.lang.Exception) {
                logger.e("SocketIoDataSource: Socket On (updateDrawer): ${e.message.orEmpty()}")
            }
        }
    }
}
