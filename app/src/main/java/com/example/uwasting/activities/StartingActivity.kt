package com.example.uwasting.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.uwasting.R
import com.example.uwasting.data.User
import com.example.uwasting.data.remote.UWastingApi
import com.example.uwasting.fragments.StartFragment
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class StartingActivity : AppCompatActivity() {

    var user: User = User()

    lateinit var uwastingApi: UWastingApi

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        configureRetrofit()

        setContentView(R.layout.activity_start)

        setFragment(StartFragment())

    }

    private fun configureRetrofit() {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl("http://192.168.72.13:5000/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()

        uwastingApi = retrofit.create(UWastingApi::class.java)

    }

    fun setFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).
        addToBackStack(fragment.tag).commit()
    }

    fun prevFragment() {
        supportFragmentManager.popBackStack()
    }

}