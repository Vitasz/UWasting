@file:Suppress("DEPRECATION", "NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")

package com.example.uwasting.activities

import MyContextWrapper
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.uwasting.R
import com.example.uwasting.data.*
import com.example.uwasting.data.remote.UWastingApi
import com.example.uwasting.fragments.CREATE_FILE_EXPENSES
import com.example.uwasting.fragments.CREATE_FILE_INCOMES
import com.example.uwasting.fragments.TabFragment
import com.example.uwasting.preferences.MyPreference
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.apache.commons.csv.CSVFormat
import org.apache.commons.csv.CSVPrinter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

// Главная активность
@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {
    lateinit var user:User
    private val compositeDisposable = CompositeDisposable()
    lateinit var uwastingApi: UWastingApi
    lateinit var myPreference: MyPreference
    lateinit var totalOperations: OperationsList
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

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode != CREATE_FILE_INCOMES&&requestCode!= CREATE_FILE_EXPENSES || resultCode != RESULT_OK) return

        val operations = if (requestCode == CREATE_FILE_INCOMES) currentOperations.selectOperationsIncomes()
        else currentOperations.selectOperationsExpenses()

        val selectedFile = data?.data

        if (selectedFile != null) {
            val writer = contentResolver.openOutputStream(selectedFile)?.bufferedWriter()
            val csvPrinter = CSVPrinter(writer, CSVFormat.DEFAULT
                .withHeader("OperationId", "Category", "Amount", "Date"))

            for (operation in operations) {
                val operationData = listOf(
                    operation.id,
                    operation.category,
                    operation.amount,
                    operation.date)

                csvPrinter.printRecord(operationData)
            }

            csvPrinter.flush()
            csvPrinter.close()


            val intent = Intent(Intent.ACTION_VIEW)
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            intent.setDataAndType(selectedFile,"text/csv")
            startActivity(Intent.createChooser(intent, "Open"));
        }
    }

    // Получение операций пользователя
    @RequiresApi(Build.VERSION_CODES.O)
    fun getOperations() {
        uwastingApi.let {
            compositeDisposable.add(uwastingApi.getOperations(user.id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    totalOperations=OperationsList(it)
                    updateCurrentOperations()

                    setFragment(TabFragment())
                }, {user.id = -1
                    myPreference.setUser(user)
                    startActivity(Intent(this, StartingActivity::class.java))
                    finish()
                }))
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        myPreference = MyPreference(this)
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
    //Нажатие кнопки назад
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