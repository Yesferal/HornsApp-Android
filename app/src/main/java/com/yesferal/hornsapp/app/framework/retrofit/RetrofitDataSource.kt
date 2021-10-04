package com.yesferal.hornsapp.app.framework.retrofit

import com.yesferal.hornsapp.core.data.abstraction.remote.BandRemoteDataSource
import com.yesferal.hornsapp.core.data.abstraction.remote.ConcertRemoteDataSource
import com.yesferal.hornsapp.core.domain.entity.Band
import com.yesferal.hornsapp.core.domain.entity.Concert
import com.yesferal.hornsapp.core.domain.common.Result
import okhttp3.ResponseBody
import retrofit2.Response
import java.lang.Exception

class RetrofitDataSource(
    private val service: Service
) : ConcertRemoteDataSource, BandRemoteDataSource {

    override suspend fun getConcerts(): Result<List<Concert>> {
        return service
            .safeCall { getConcerts() }
            .mapToResult {
                it.map { apiConcert -> apiConcert.mapToConcert() }
            }
    }

    override suspend fun getConcert(
        id: String
    ): Result<Concert> {
        return service
            .safeCall { getConcertBy(id) }
            .mapToResult { it.mapToConcert() }
    }

    override suspend fun getBand(
        id: String
    ): Result<Band> {
        return service
            .safeCall { getBandBy(id) }
            .mapToResult { it.mapToBand() }
    }

    private suspend fun <INPUT> Service.safeCall(
        request: suspend Service.() -> Response<INPUT>
    ): Response<INPUT> {
        return try {
            request()
        } catch (e: Exception) {
            Response.error(404, ResponseBody.create(null, String()))
        }
    }

    private fun <INPUT, OUTPUT> Response<INPUT>.mapToResult(
        func: Response<INPUT>.(INPUT) -> OUTPUT
    ): Result<OUTPUT> {
        return if (isSuccessful) {
            body()?.let {
                Result.Success(func(it))
            } ?: Result.Error
        } else {
            Result.Error
        }
    }
}
