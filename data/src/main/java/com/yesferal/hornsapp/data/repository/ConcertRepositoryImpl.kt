package com.yesferal.hornsapp.data.repository

import com.yesferal.hornsapp.data.datasource.storage.StorageDataSource
import com.yesferal.hornsapp.data.datasource.api.ApiDataSource
import com.yesferal.hornsapp.data.datasource.api.response.mapToConcert
import com.yesferal.hornsapp.domain.entity.Concert
import com.yesferal.hornsapp.domain.repository.ConcertRepository

class ConcertRepositoryImpl(
    private val storageDataSource: StorageDataSource,
    private val apiDataSource: ApiDataSource
) : ConcertRepository {

    override fun getConcert(
        onSuccess: (entities: List<Concert>) -> Unit,
        onError: (t: Throwable) -> Unit
    ) {
        apiDataSource.getConcerts(
            onSuccess = {
                val entities = it.map { response ->
                    response.mapToConcert()
                }

                onSuccess(entities)
            }, onError = {
                onError(it)
            }
        )
    }
}