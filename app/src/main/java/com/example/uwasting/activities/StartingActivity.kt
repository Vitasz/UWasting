package com.example.uwasting.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.uwasting.R
import com.example.uwasting.data.Constants
import com.example.uwasting.data.OnBackButtonListener
import com.example.uwasting.data.User
import com.example.uwasting.data.remote.UWastingApi
import com.example.uwasting.fragments.StartFragment
import com.example.uwasting.preferences.MyPreference
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


class StartingActivity : AppCompatActivity() {

    var user: User = User()
    lateinit var myPreference:MyPreference
    lateinit var uwastingApi: UWastingApi

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)
        configureRetrofit()
        //setContentView(R.layout.activity_start)
        myPreference = MyPreference(this)
        user = myPreference.getUser()
        if (user.id!=-1){
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        setFragment(StartFragment())


    }

    // Натсройка подключения к серверу
    private fun configureRetrofit() {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(Constants.APIUrl)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()

        uwastingApi = retrofit.create(UWastingApi::class.java)

    }

    // Переключение фрагмента
    fun setFragment(fragment: Fragment) {

        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).
        addToBackStack(fragment.tag).commit()

    }

    // Предыдущий фрагмент
    fun prevFragment() {
        supportFragmentManager.popBackStack()
    }

    override fun onBackPressed() {
        val backStackCount = supportFragmentManager.backStackEntryCount

        // Находим текущий Фрагмент и вызваем его метод: onBackPressed()

        // Находим текущий Фрагмент и вызваем его метод: onBackPressed()
        if (backStackCount > 0) {
            val currentFragment = supportFragmentManager.findFragmentById(R.id.fragment_container)
            if (currentFragment is OnBackButtonListener) {
                val actionResult = currentFragment.onBackPressed()

                if (actionResult) {
                    return
                }
            }
        }

        super.onBackPressed()
    }
}