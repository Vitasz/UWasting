package com.example.uwasting.data.remote

import io.reactivex.Single
import retrofit2.http.*

interface UWastingApi {

    @GET("/GetByLoginAndPassword")
    @Headers("Content-Type: application/json")
    fun getUserData(@Query("login") email: String, @Query("password") password: String): Single<String>


    @POST("./RegistrateUser")
    fun saveUserData(email: String, password: String, name: String, surname: String) {

    }
}