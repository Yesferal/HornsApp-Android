package com.yesferal.hornsapp.app.framework.retrofit

import com.yesferal.hornsapp.data.abstraction.ApiDataSource
import com.yesferal.hornsapp.domain.common.Result
import com.yesferal.hornsapp.domain.entity.Band
import com.yesferal.hornsapp.domain.entity.Concert
import retrofit2.Response

class RetrofitDataSource(
    private val service: Service
) : ApiDataSource {
    override suspend fun getConcerts(): Result<List<Concert>> {
        return service.getConcerts()
                .safeCall { response ->
                    response.map { it.mapToConcert() }
                }
    }

    override suspend fun getConcert(
        id: String
    ): Result<Concert> {
        return service.getConcertBy(id)
                .safeCall { it.mapToConcert() }
    }

    override suspend fun getBand(
        id: String
    ): Result<Band> {
        return service.getBandBy(id)
                .safeCall { it.mapToBand() }
    }

    private fun <INPUT, OUTPUT> Response<INPUT>.safeCall(
            func: Response<INPUT>.(INPUT) -> OUTPUT
    ): Result<OUTPUT> {
        return if (isSuccessful) {
            body()?.let {
                Result.Success(func(it))
            }?: Result.Error
        } else {
            Result.Error
        }
    }
}