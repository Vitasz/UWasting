package com.example.uwasting.data.remote

import com.example.uwasting.data.User
import io.reactivex.Single
import retrofit2.http.*

interface UWastingApi {

    @GET("/GetByLoginAndPassword")
    @Headers("Content-Type: application/json")
    fun getUserData(@Query("login") email: String, @Query("password") password: String): Single<User>


    @POST("./RegistrateUser")
    fun saveUserData(email: String, password: String, name: String, surname: String) {

    }
}