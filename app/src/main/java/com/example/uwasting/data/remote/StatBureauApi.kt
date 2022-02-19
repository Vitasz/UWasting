package com.example.uwasting.data.remote

import com.example.uwasting.data.User
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface StatBureauApi {
    @GET("/get-data-json")
    @Headers("Content-Type: application/json")
    fun getIndex(@Query("country") country: String): Single<List<StatBureauData>>
}