package com.example.uwasting.data.remote

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

// API для СтатБюро
interface StatBureauApi {
    @GET("/get-data-json")
    @Headers("Content-Type: application/json")
    fun getIndex(@Query("country") country: String): Single<List<StatBureauData>>
}