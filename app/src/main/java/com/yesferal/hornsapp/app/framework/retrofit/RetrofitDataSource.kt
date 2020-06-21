package com.yesferal.hornsapp.app.framework.retrofit

import com.yesferal.hornsapp.app.framework.retrofit.entity.GetConcerts
import com.yesferal.hornsapp.app.framework.retrofit.entity.mapToConcert
import com.yesferal.hornsapp.data.abstraction.ApiDataSource
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
            override fun onFailure(
                call: Call<List<GetConcerts>>,
                t: Throwable
            ) {
                onError(t)
            }

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
        })
    }
}