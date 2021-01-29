package com.yesferal.hornsapp.app.framework.retrofit

import com.yesferal.hornsapp.app.framework.retrofit.entity.*
import com.yesferal.hornsapp.data.abstraction.ApiDataSource
import com.yesferal.hornsapp.domain.common.Result
import com.yesferal.hornsapp.domain.entity.Band
import com.yesferal.hornsapp.domain.entity.Concert
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RetrofitDataSource(
    private val service: Service
) : ApiDataSource {
    override fun getConcerts(
        onSuccess: (entity: List<Concert>) -> Unit,
        onError: (t: Throwable) -> Unit
    ) {
        val call = service.getConcerts()
        call.enqueue(object : Callback<List<GetConcerts>> {
            override fun onResponse(
                call: Call<List<GetConcerts>>,
                response: Response<List<GetConcerts>>
            ) {
                val data = response.body()?: listOf()
                val concerts = data.map {
                    it.mapToConcert()
                }

                onSuccess(concerts)
            }

            override fun onFailure(
                call: Call<List<GetConcerts>>,
                t: Throwable
            ) {
                onError(t)
            }
        })
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