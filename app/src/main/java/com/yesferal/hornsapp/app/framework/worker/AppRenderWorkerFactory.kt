/* Copyright © 2026 HornsApp. All rights reserved. */
package com.yesferal.hornsapp.app.framework.worker

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import com.yesferal.hornsapp.app.framework.packageinfo.PackageInfoDataSource
import com.yesferal.hornsapp.app.framework.retrofit.RetrofitDataSource
import com.yesferal.hornsapp.core.domain.abstraction.Logger

class AppRenderWorkerFactory(
    private val retrofitDataSource: RetrofitDataSource,
    private val logger: Logger,
    private val packageInfoDataSource: PackageInfoDataSource,
) : WorkerFactory() {

    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters
    ): ListenableWorker? {

        return when (workerClassName) {
            AppRenderWorker::class.java.name ->
                AppRenderWorker(
                    appContext,
                    workerParameters,
                    retrofitDataSource,
                    logger,
                    packageInfoDataSource,
                )
            else -> null
        }
    }
}