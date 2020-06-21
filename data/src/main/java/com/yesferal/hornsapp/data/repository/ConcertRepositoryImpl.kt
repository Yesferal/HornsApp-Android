package com.yesferal.hornsapp.data.repository

import com.yesferal.hornsapp.domain.abstraction.ConcertRepository
import com.yesferal.hornsapp.data.abstraction.StorageDataSource
import com.yesferal.hornsapp.data.abstraction.ApiDataSource
import com.yesferal.hornsapp.domain.entity.Concert

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
                onSuccess(it)
            }, onError = {
                onError(it)
            }
        )
    }
}