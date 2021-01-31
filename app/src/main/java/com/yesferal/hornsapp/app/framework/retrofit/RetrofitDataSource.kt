package com.yesferal.hornsapp.app.framework.retrofit

import com.yesferal.hornsapp.data.abstraction.ApiDataSource
import com.yesferal.hornsapp.domain.common.Result
import com.yesferal.hornsapp.domain.entity.Band
import com.yesferal.hornsapp.domain.entity.Concert
import okhttp3.ResponseBody
import retrofit2.Response
import java.lang.Exception

class RetrofitDataSource(
    private val service: Service
) : ApiDataSource {
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
            }?: Result.Error
        } else {
            Result.Error
        }
    }
}