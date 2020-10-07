package com.yesferal.hornsapp.app.framework.retrofit

import com.yesferal.hornsapp.app.framework.retrofit.entity.*
import com.yesferal.hornsapp.data.abstraction.ApiDataSource
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

    override fun getConcert(
        id: String,
        onSuccess: (entity: Concert) -> Unit,
        onError: (t: Throwable) -> Unit
    ) {
        val call = service.getConcertBy(id)
        call.enqueue(object : Callback<GetConcert> {
            override fun onResponse(
                call: Call<GetConcert>,
                response: Response<GetConcert>
            ) {
                val data = response.body()
                data?.let {
                    onSuccess(it.mapToConcert())
                }
            }

            override fun onFailure(
                call: Call<GetConcert>,
                t: Throwable
            ) {
                onError(t)
            }
        })
    }

    override fun getBand(
        id: String,
        onSuccess: (entity: Band) -> Unit,
        onError: (t: Throwable) -> Unit
    ) {
        val call = service.getBandBy(id)
        call.enqueue(object : Callback<GetBand> {
            override fun onResponse(
                call: Call<GetBand>,
                response: Response<GetBand>
            ) {
                val data = response.body()
                data?.let {
                    onSuccess(it.mapToBand())
                }
            }

            override fun onFailure(
                call: Call<GetBand>,
                t: Throwable
            ) {
                onError(t)
            }
        })
    }
}