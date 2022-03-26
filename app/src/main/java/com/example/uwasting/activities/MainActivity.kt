@file:Suppress("DEPRECATION")

package com.example.uwasting.activities

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
    var language = "strings-ru"
    val user: User = User()
    private val compositeDisposable = CompositeDisposable()
    lateinit var uwastingApi: UWastingApi
    private lateinit var totalOperations: OperationsList
    lateinit var currentOperations: OperationsList
    var period = 30
    var curr = "$"
    var ue = 1
    var index = 0f

    override fun onResume() {

        changeLanguage()
        super.onResume()
    }

    private fun changeLanguage(){
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(applicationContext)
        Toast.makeText(applicationContext, language, Toast.LENGTH_SHORT).show()
        if(language=="strings-ru"){
            Toast.makeText(applicationContext,"Russian",Toast.LENGTH_SHORT).show()
            language("")
        }else if(language=="strings-en"){
            Toast.makeText(applicationContext,"English",Toast.LENGTH_SHORT).show()
            language("strings-en")
        }
    }


    private fun language(language: String){
        val locale = Locale(language)
        Locale.setDefault(locale)
        val resources = resources
        val configuration = resources.configuration
        configuration.locale = locale
        resources.updateConfiguration(configuration, resources.displayMetrics)

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
        val arguments = intent.extras
        arguments!!.let {
            user.name = it.getString("UserName", "NOT FOUND")
            user.surname = it.getString("UserSurName", "NOT FOUND")
            user.email = it.getString("UserEmail", "NOT FOUND")
            user.id = it.getInt("UserId", -1)
        }
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