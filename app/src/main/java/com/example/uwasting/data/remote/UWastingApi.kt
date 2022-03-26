package com.example.uwasting.data.remote

import com.example.uwasting.data.Operation
import com.example.uwasting.data.User
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

// API для сервера приложения
interface UWastingApi {

    @GET("/GetByLoginAndPassword")
    @Headers("Content-Type: application/json")
    fun getUserData(@Query("login") email: String, @Query("password") password: String): Single<User>

    @GET("/FindLoginInDB")
    @Headers("Content-Type:application/json")
    fun checkEmail(@Query("login") email: String): Single<Boolean>


    @GET("/RegistrateUser")
    @Headers("Content-Type: application/json")
    fun registrateUser(@Query("login") email:String,
                            @Query("password") password:String,
                            @Query("name") name:String,
                            @Query("surname")surname:String): Single<User>

    @GET("/СhangeNameSurname")
    @Headers("Content-Type: application/json")
    fun changeNameSurname(@Query("id") id:Int,
                       @Query("name") newName:String,
                       @Query("surname") newSurname:String): Single<Boolean>

    @GET("/ChangeLogin")
    @Headers("Content-Type: application/json")
    fun changeLogin(@Query("id") id:Int,
                          @Query("login") email:String): Single<Boolean>

    @GET("/ChangePassword")
    @Headers("Content-Type: application/json")
    fun changePassword(@Query("id") id:Int,
                          @Query("password") password:String): Single<Boolean>

    @GET("/GetOperations")
    @Headers("Content-Type: application/json")
    fun getOperations(@Query("UserId")id:Int):Single<ArrayList<Operation>>

    @GET("/AddOperation")
    @Headers("Content-Type: application/json")
    fun addOperation(@Query("value")value:Int, @Query("category")category: String, @Query("date") date: String, @Query("id")id:Int):Single<Boolean>

    @GET("/DeleteOperation")
    @Headers("Content-Type: application/json")
    fun deleteOperation(@Query("id")id:Int):Single<Boolean>
}