@file:Suppress("DEPRECATION")

package com.example.uwasting.activities

import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.preference.PreferenceManager
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.uwasting.R
import com.example.uwasting.data.Constants
import com.example.uwasting.data.OperationsList
import com.example.uwasting.data.User
import com.example.uwasting.data.remote.UWastingApi
import com.example.uwasting.fragments.TabFragment
import com.example.uwasting.preferences.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import kotlin.collections.ArrayList

// Главная активность
@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {
    lateinit var user:User
    private val compositeDisposable = CompositeDisposable()
    lateinit var uwastingApi: UWastingApi
    lateinit var myPreference: MyPreference
    private lateinit var totalOperations: OperationsList
    lateinit var currentOperations: OperationsList
    var period = 30
    var curr = "$"
    var ue = 1
    var index = 0f
    override fun attachBaseContext(newBase: Context?) {
        myPreference = MyPreference(newBase!!)
        val lang = myPreference.getLanguage()
        super.attachBaseContext(MyContextWrapper.wrap(newBase, lang))
    }
    // Обновление операций
    @RequiresApi(Build.VERSION_CODES.O)
    fun updateCurrentOperations(){
        currentOperations = OperationsList(ArrayList(totalOperations.selectOperations(period)))
    }

    // Получение операций пользователя
    @RequiresApi(Build.VERSION_CODES.O)
    fun getOperations(){
        uwastingApi.let {
            compositeDisposable.add(uwastingApi.getOperations(user.id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    totalOperations=OperationsList(it)
                    updateCurrentOperations()

                    setFragment(TabFragment())
                }, {
                }))
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        myPreference=MyPreference(this)
        user = myPreference.getUser()
        configureRetrofit()
        getOperations()

    }

    // Получение разрешения на создание файлов в памяти системы
    fun checkPermission(permission: String, requestCode: Int) {
        if (ContextCompat.checkSelfPermission(this@MainActivity, permission) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(this@MainActivity, arrayOf(permission), requestCode)
        }
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
}