/* Copyright © 2023 HornsApp. All rights reserved. */
package com.yesferal.hornsapp.app.framework.retrofit

import com.yesferal.hornsapp.app.presentation.di.SettingFlavorDataClass
import com.yesferal.hornsapp.core.data.abstraction.remote.BandRemoteDataSource
import com.yesferal.hornsapp.core.data.abstraction.remote.ConcertRemoteDataSource
import com.yesferal.hornsapp.core.data.abstraction.remote.RenderRemoteDataSource
import com.yesferal.hornsapp.core.data.abstraction.remote.ReviewRemoteDataSource
import com.yesferal.hornsapp.core.data.abstraction.storage.RenderStorageDataSource
import com.yesferal.hornsapp.core.domain.abstraction.Logger
import com.yesferal.hornsapp.core.domain.entity.Band
import com.yesferal.hornsapp.core.domain.entity.Concert
import com.yesferal.hornsapp.core.domain.entity.Lineup
import com.yesferal.hornsapp.core.domain.entity.render.AppRender
import com.yesferal.hornsapp.core.domain.entity.render.CategoryRender
import com.yesferal.hornsapp.core.domain.entity.render.ScreenRender
import com.yesferal.hornsapp.core.domain.usecase.LineupUseCase
import com.yesferal.hornsapp.core.domain.util.HaResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class RetrofitDataSource(
    private val service: Service,
    private val settingFlavorDataClass: SettingFlavorDataClass,
    private val renderStorageDataSource: RenderStorageDataSource,
    private val logger: Logger,
) : ConcertRemoteDataSource, BandRemoteDataSource, ReviewRemoteDataSource, LineupUseCase, RenderRemoteDataSource {

    override suspend fun getConcerts(): HaResult<List<Concert>> {
        return service
            .safeCall { getConcerts(settingFlavorDataClass.eventsPath) }
            .mapToResult {
                it.map { apiConcert -> apiConcert.mapToConcert() }
            }
    }

    override suspend fun getConcert(
        id: String
    ): HaResult<Concert> {
        return service
            .safeCall { getConcertBy(id) }
            .mapToResult { it.mapToConcert() }
    }

    override suspend fun getBand(
        id: String
    ): HaResult<Band> {
        return service
            .safeCall { getBandBy(id) }
            .mapToResult { it.mapToBand() }
    }

    override suspend fun getReview(
        id: String
    ): HaResult<ScreenRender> {
        return service
            .safeCall { getScreenRenderBy(id) }
            .mapToResult { it }
    }

    override suspend fun getLineup(id: String): HaResult<Lineup> {
        return service
            .safeCall { getLineupBy(id) }
            .mapToResult { it.mapToLineup() }
    }

    private val _homeRender =
        MutableStateFlow(renderStorageDataSource.getAppRender()?.screens ?: listOf())
    override val homeRender: StateFlow<List<ScreenRender>>
        get() = _homeRender

    private val _categoryRender =
        MutableStateFlow(renderStorageDataSource.getAppRender()?.categories ?: listOf())
    override val categoryRender: StateFlow<List<CategoryRender>>
        get() = _categoryRender

    suspend fun getAppRender(platform: String, appVersion: Long, appId: String) {
        val result: HaResult<AppRender> = service
            .safeCall { getAppRender(platform, appVersion, appId) }
            .mapToResult { it }

        when (result) {
            is HaResult.Success -> {
                logger.d("RetrofitDataSource: getAppRender: result.value: ${result.value}")
                val remoteDocAppVersion = result.value.appVersion ?: 0
                val localDocAppVersion = renderStorageDataSource.getAppRender()?.appVersion ?: 0
                logger.d("RetrofitDataSource: getAppRender: remoteDocAppVersion: ${remoteDocAppVersion}")
                logger.d("RetrofitDataSource: getAppRender: localDocAppVersion: ${localDocAppVersion}")

                val remoteDocVersion = result.value.docVersion ?: 0
                val localDocVersion = renderStorageDataSource.getAppRender()?.docVersion ?: 0
                logger.d("RetrofitDataSource: getAppRender: remoteDocVersion: ${remoteDocVersion}")
                logger.d("RetrofitDataSource: getAppRender: localDocVersion: ${localDocVersion}")
                if ((remoteDocAppVersion == localDocAppVersion && remoteDocVersion > localDocVersion) ||
                    remoteDocAppVersion > localDocAppVersion) {
                    renderStorageDataSource.updateAppRender(result.value)
                    result.value.screens?.let { screens ->
                        _homeRender.value = screens
                    }
                    result.value.categories?.let { categories ->
                        _categoryRender.value = categories
                    }
                }
            }
            is HaResult.Error -> {
                logger.e("RetrofitDataSource: Fetch the AppRender document failed")
            }
        }
    }
}
