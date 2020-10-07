package com.yesferal.hornsapp.data.repository

import com.yesferal.hornsapp.data.abstraction.ApiDataSource
import com.yesferal.hornsapp.domain.abstraction.BandRepository
import com.yesferal.hornsapp.domain.entity.Band

class BandRepositoryImpl(
    private val apiDataSource: ApiDataSource
) : BandRepository{

    override fun getBand(
        id: String,
        onSuccess: (band: Band) -> Unit,
        onError: (t: Throwable) -> Unit
    ) {
        apiDataSource.getBand(
            id,
            onSuccess = {
                onSuccess(it)
            },
            onError = {
                onError(it)
            }
        )
    }
}