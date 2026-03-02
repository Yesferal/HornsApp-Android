/* Copyright © 2026 HornsApp. All rights reserved. */
package com.yesferal.hornsapp.app.framework.messaging.strategy

import android.content.Context
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.google.gson.Gson
import com.yesferal.hornsapp.app.framework.worker.AppRenderWorker
import com.yesferal.hornsapp.core.domain.abstraction.Logger
import java.util.concurrent.TimeUnit
import kotlin.random.Random

class AppRenderUpdatePushHandler(
    private val context: Context,
    private val gson: Gson,
    private val logger: Logger
): PushHandler {
    companion object {
        val APP_ID = "appId"
        val APP_VERSION = "appVersion"
    }

    data class AppRenderPushData(
        val appId: String?,
        val appVersion: Int?,
        val maxRefreshDelay: Long?,
    )

    override fun canHandle(type: String?) =
        type == "APP_RENDER_UPDATE"

    override fun handle(data: Map<String, String>) {
        logger.d("FCM: AppRenderUpdate: Message received: ${data}")

        val appRenderPushData = gson.fromJson(data.toString(), AppRenderPushData::class.java)

        if (appRenderPushData.appVersion != null && appRenderPushData.appId != null) {
            val inputData = workDataOf(
                APP_VERSION to appRenderPushData.appVersion,
                APP_ID to appRenderPushData.appId
            )

            val maxRefreshDelay: Long = appRenderPushData.maxRefreshDelay ?: 30
            val delaySeconds = Random.nextLong(0, maxRefreshDelay)
            val request = OneTimeWorkRequestBuilder<AppRenderWorker>()
                .setInputData(inputData)
                .setInitialDelay(delaySeconds, TimeUnit.SECONDS)
                .build()

            WorkManager.getInstance(context)
                .enqueue(request)
        }
    }
}
