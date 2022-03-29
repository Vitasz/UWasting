@file:Suppress("DEPRECATION")

package com.example.uwasting.fragments

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.DocumentsContract
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.uwasting.R
import com.example.uwasting.activities.MainActivity
import com.example.uwasting.data.Category
import com.example.uwasting.data.CategoryRecyclerView
import com.example.uwasting.data.Constants
import com.example.uwasting.data.OnItemClickListener
import com.example.uwasting.data.remote.StatBureauApi
import com.example.uwasting.dialogs.PeriodDialog
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.google.android.material.button.MaterialButton
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
import java.io.File
import java.nio.file.Files
import java.nio.file.Paths
import kotlin.math.pow
import kotlin.math.round

const val CREATE_FILE_INCOMES = 111
const val CREATE_FILE_EXPENSES = 112

interface UpdateFragment{
    fun update()
}
// Фрагмент с доходами
class IncomesFragment : Fragment(), OnItemClickListener, UpdateFragment {
    private lateinit var pieChart: PieChart
    private lateinit var totalIncomesTxt:TextView
    private lateinit var recyclerView:RecyclerView
    private lateinit var dateTxt: TextView
    private lateinit var balanceView:TextView
    private lateinit var mainActivity: MainActivity
    private lateinit var statBureauApi: StatBureauApi
    private var compositeDisposable = CompositeDisposable()
    override fun onItemClicked(item: Triple<Category, Int, Int>){
        mainActivity.setFragment(CategoryFragment(item.first, true))
    }
    @SuppressLint("SetTextI18n")
    private fun configureRetrofit() {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(Constants.StatBureauUrl)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()

        statBureauApi = retrofit.create(StatBureauApi::class.java)
        statBureauApi.getIndex("russia")

        statBureauApi.let {
            compositeDisposable.add(statBureauApi.getIndex("russia")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    mainActivity.index = it[0].inflationRate
                    val sumIncomes = mainActivity.currentOperations.getTotalSumIncomes()
                    val sumExpenses = mainActivity.currentOperations.getTotalSumExpenses()
                    val balance = (sumIncomes + sumExpenses) / (((mainActivity.index + 100) / 100).pow(mainActivity.period / 30))

                    balanceView.text = mainActivity.getString(R.string.balance) + " " + String.format("%.2f", balance/mainActivity.ue)+mainActivity.curr
                }, {
                }))
        }
    }

    @SuppressLint("SetTextI18n")
    fun updateOperations(){

        totalIncomesTxt.text = '+' + (round(mainActivity.currentOperations.getTotalSumIncomes().toFloat()/mainActivity.ue*100) /100.0).toString()+mainActivity.curr
        //Список с категориями
        recyclerView.layoutManager = LinearLayoutManager(mainActivity)
        recyclerView.adapter = CategoryRecyclerView(mainActivity.currentOperations.combineByCategoryIncomes(), this, mainActivity)

        //Диаграмма
        loadPieChartData()

        val sumIncomes = mainActivity.currentOperations.getTotalSumIncomes()
        val sumExpenses = mainActivity.currentOperations.getTotalSumExpenses()
        val balance = (sumIncomes + sumExpenses) / (((mainActivity.index + 100) / 100).pow(mainActivity.period / 30))

        balanceView.text = mainActivity.getString(R.string.balance) + " " + String.format("%.2f", balance/mainActivity.ue)+mainActivity.curr

    }
    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("SetTextI18n", "SdCardPath")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mainActivity = activity as MainActivity
        val view = inflater.inflate(R.layout.fragment_incomes, container, false)
        if (mainActivity.index==0f)configureRetrofit()
        totalIncomesTxt = view.findViewById(R.id.sum_txt)
        val exportToCSVBtn = view.findViewById<Button>(R.id.export_btn)
        val periodLayout = view.findViewById<ConstraintLayout>(R.id.period_layout)
        val addIncomeBtn = view.findViewById<MaterialButton>(R.id.add_income_btn)
        balanceView = view.findViewById(R.id.balance_inc)
        dateTxt = view.findViewById(R.id.date_txt)
        dateTxt.text = getString(R.string.last) + " " + mainActivity.period + " " + getString(R.string.days)
        recyclerView = view.findViewById(R.id.categories_list)
        pieChart = view.findViewById(R.id.diagram_incomes)
        setupPieChart()
        updateOperations()

        // Нажатие на период
        periodLayout.setOnClickListener {
            val dialog = PeriodDialog(mainActivity, this)
            dialog.show(parentFragmentManager, "period")
        }

        addIncomeBtn.setOnClickListener {
            mainActivity.setFragment(NewIncomeFragment())
        }

        exportToCSVBtn.setOnClickListener {

            val intent = Intent(Intent.ACTION_CREATE_DOCUMENT).apply {
                addCategory(Intent.CATEGORY_OPENABLE)
                type = "text/csv"
                putExtra(Intent.EXTRA_TITLE, "incomes.csv")
            }
            mainActivity.startActivityForResult(intent, CREATE_FILE_INCOMES)
        }

        return view
    }

    // Настройка отображения диаграммы
    private fun setupPieChart() {
        pieChart.isDrawHoleEnabled = false
        pieChart.setUsePercentValues(true)

        pieChart.setDrawEntryLabels(false)
        pieChart.centerText = ""
        pieChart.description.isEnabled = false

        val legend = pieChart.legend
        legend.verticalAlignment = Legend.LegendVerticalAlignment.TOP
        legend.horizontalAlignment = Legend.LegendHorizontalAlignment.RIGHT
        legend.orientation = Legend.LegendOrientation.VERTICAL
        legend.setDrawInside(false)
        legend.isEnabled = false
    }

    // Загрузка информации в диаграмму
    private fun loadPieChartData(){
        val entries = ArrayList<PieEntry>()
        val operations = mainActivity.currentOperations.combineByCategoryIncomes()

        val colors = ArrayList<Int>()
        for(i in operations) {
            entries.add(PieEntry(i.third.toFloat(), i.first.name))
            colors.add(i.first.color)
        }

        val dataSet = PieDataSet(entries, "")
        dataSet.colors = colors

        val data = PieData(dataSet)
        data.setDrawValues(false)

        pieChart.data = data
        pieChart.invalidate()
        pieChart.animateY(1000, Easing.EaseInOutQuad)
    }

    @SuppressLint("SetTextI18n")
    override fun update() {
        dateTxt.text = "Последние ${mainActivity.period} дней"
        updateOperations()
    }
}