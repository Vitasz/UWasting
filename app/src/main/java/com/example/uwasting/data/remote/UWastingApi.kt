package com.example.uwasting.data.remote

import com.example.uwasting.data.Operation
import com.example.uwasting.data.User
import com.example.uwasting.fragments.ChangePasswordFragment
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

    @GET("/СhangeNameSurname")
    @Headers("Content-Type: application/json")
    fun ChangeNameSurname(@Query("id") id:Int,
                       @Query("name") newName:String,
                       @Query("surname") newSurname:String): Single<Boolean>

    @GET("/ChangeLogin")
    @Headers("Content-Type: application/json")
    fun ChangeLogin(@Query("id") id:Int,
                          @Query("login") email:String): Single<Boolean>

    @GET("/ChangePassword")
    @Headers("Content-Type: application/json")
    fun ChangePassword(@Query("id") id:Int,
                          @Query("password") password:String): Single<Boolean>

    @GET("/GetOperations")
    @Headers("Content-Type: application/json")
    fun GetOperations(@Query("UserId")id:Int):Single<List<Operation>>
}