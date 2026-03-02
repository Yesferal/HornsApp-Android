/* Copyright © 2026 HornsApp. All rights reserved. */
package com.yesferal.hornsapp.app.framework.worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.yesferal.hornsapp.app.framework.messaging.strategy.AppRenderUpdatePushHandler
import com.yesferal.hornsapp.app.framework.packageinfo.PackageInfoDataSource
import com.yesferal.hornsapp.app.framework.retrofit.RetrofitDataSource
import com.yesferal.hornsapp.app.presentation.di.SettingFlavorDataClass
import com.yesferal.hornsapp.core.domain.abstraction.Logger

class AppRenderWorker(
    appContext: Context,
    workerParams: WorkerParameters,
    private val retrofitDataSource: RetrofitDataSource,
    private val logger: Logger,
    private val packageInfoDataSource: PackageInfoDataSource,
) : CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result {
        val docAppVersion = inputData.getInt(AppRenderUpdatePushHandler.APP_VERSION, -1)
        if (docAppVersion <= 0) return Result.failure()

        val appId = inputData.getString(AppRenderUpdatePushHandler.APP_ID)
            ?: return Result.failure()

        val currentAppVersion = packageInfoDataSource.getVersionCode()

        return if (docAppVersion <= currentAppVersion) {
            retrofitDataSource.getAppRender(SettingFlavorDataClass.PLATFORM, currentAppVersion, appId)
            Result.success()
        } else {
            Result.failure()
        }
    }
}