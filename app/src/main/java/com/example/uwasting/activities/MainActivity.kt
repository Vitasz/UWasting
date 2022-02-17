package com.example.uwasting.activities

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.uwasting.R
import com.example.uwasting.data.Constants
import com.example.uwasting.data.Operation
import com.example.uwasting.data.OperationsList
import com.example.uwasting.data.User
import com.example.uwasting.data.remote.UWastingApi
import com.example.uwasting.fragments.TabFragment
import com.google.android.material.appbar.MaterialToolbar
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

// Главная активность
class MainActivity : AppCompatActivity() {
    val user: User = User()
    private val compositeDisposable = CompositeDisposable()
    lateinit var uwastingApi: UWastingApi
    lateinit var totalOperations: OperationsList
    lateinit var currentOperations: OperationsList
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
        uwastingApi?.let {
            compositeDisposable.add(uwastingApi.GetOperations(user.id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    totalOperations=OperationsList(it)
                    currentOperations=OperationsList(totalOperations.SelectOperations(90))
                    setFragment(TabFragment())
                }, {
                }))
        }

    }

    fun checkPermission(permission: String, requestCode: Int) {
        if (ContextCompat.checkSelfPermission(this@MainActivity, permission) == PackageManager.PERMISSION_DENIED) {
            // Requesting the permission
            ActivityCompat.requestPermissions(this@MainActivity, arrayOf(permission), requestCode)
        }
    }

    private fun configureRetrofit() {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(Constants.APIurl)
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