package com.example.uwasting.data.remote

import com.example.uwasting.data.User
import io.reactivex.Single
import retrofit2.http.*

interface UWastingApi {

    @GET("/GetByLoginAndPassword")
    @Headers("Content-Type: application/json")
    fun getUserData(@Query("login") email: String, @Query("password") password: String): Single<User>

    @GET("/FindLoginInDB")
    @Headers("Content-Type:application/json")
    fun checkEmail(@Query("login") email: String): Single<Boolean>


    @GET("/RegistrateUser")
    @Headers("Content-Type: application/json")
    fun RegistrateUser(@Query("login") email:String,
                            @Query("password") password:String,
                            @Query("name") name:String,
                            @Query("surname")surname:String): Single<Boolean>
}