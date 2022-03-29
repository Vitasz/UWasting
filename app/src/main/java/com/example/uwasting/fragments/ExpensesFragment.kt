package com.example.uwasting.fragments

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.uwasting.R
import com.example.uwasting.activities.MainActivity
import com.example.uwasting.data.Category

import com.example.uwasting.data.CategoryRecyclerView
import com.example.uwasting.data.LineReg
import com.example.uwasting.data.OnItemClickListener
import com.example.uwasting.dialogs.PeriodDialog
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry

import org.apache.commons.csv.CSVFormat
import org.apache.commons.csv.CSVPrinter
import java.io.File
import java.nio.file.Files
import java.nio.file.Paths
import kotlin.math.abs
import kotlin.math.round

// Фрагмент с расходами
class ExpensesFragment : Fragment(), OnItemClickListener, UpdateFragment {
    private lateinit var pieChart: PieChart
    private lateinit var mainActivity:MainActivity
    private lateinit var dateTxt:TextView
    private lateinit var recyclerView:RecyclerView
    private lateinit var totalExpensesTxt:TextView
    private lateinit var forecastView:TextView

    // Обновить операции
    @SuppressLint("SetTextI18n")
    fun updateOperations(){
        loadPieChartData()
        totalExpensesTxt.text = (round(mainActivity.currentOperations.getTotalSumExpenses().toFloat()/mainActivity.ue*100)/100.0).toString()+mainActivity.curr
        val expenses = mainActivity.currentOperations.selectOperationsExpenses()
        val expensesRight = ArrayList<Int>()
        for (i in expenses)expensesRight.add(abs(i.amount))
        val incomes = mainActivity.currentOperations.selectOperationsIncomes()
        val incomesRight = ArrayList<Int>()
        for (i in incomes)incomesRight.add(abs(i.amount))

        val lineReg = LineReg(incomesRight, expensesRight)
        val pred = lineReg.evaluateAlgorithm()
        forecastView.text = mainActivity.getString(R.string.monthly_forecast)+": -"+ String.format("%.2f", pred/mainActivity.ue)+mainActivity.curr

        recyclerView.layoutManager = LinearLayoutManager(mainActivity)
        recyclerView.adapter = CategoryRecyclerView(mainActivity.currentOperations.combineByCategoryExpenses(), this, mainActivity)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_expenses, container, false)
        mainActivity = activity as MainActivity

        // Получение виджетов
        val exportToCSVBtn = view.findViewById<Button>(R.id.export_btn)
        val addExpenseBtn = view.findViewById<Button>(R.id.add_expense_btn)
        val periodLayout = view.findViewById<ConstraintLayout>(R.id.period_layout)
        recyclerView = view.findViewById(R.id.categories_list)
        dateTxt = view.findViewById(R.id.date_txt)
        dateTxt.text = getString(R.string.last) + " " + mainActivity.period + " " + getString(R.string.days)
        totalExpensesTxt = view.findViewById(R.id.totalExpenses)
        forecastView=view.findViewById(R.id.forecast)
        pieChart = view.findViewById(R.id.diagram_expenses)
        setupPieChart()
        // Нажатие на период
        periodLayout.setOnClickListener {
            val dialog = PeriodDialog(mainActivity, this)
            dialog.show(parentFragmentManager, "period")
        }

        updateOperations()

        // Добавление расхода
        addExpenseBtn.setOnClickListener {
            mainActivity.setFragment(NewExpenseFragment())
        }

        // Экспорт расходов в CSV файл
        exportToCSVBtn.setOnClickListener {
            val intent = Intent(Intent.ACTION_CREATE_DOCUMENT).apply {
                addCategory(Intent.CATEGORY_OPENABLE)
                type = "text/csv"
                putExtra(Intent.EXTRA_TITLE, "expenses.csv")
            }
            mainActivity.startActivityForResult(intent, CREATE_FILE_EXPENSES)
        }

        return view
    }

    // Настройка диаграммы
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

    // Загрузка данных в диаграмму
    private fun loadPieChartData(){
        val mainActivity = activity as MainActivity
        val entries = ArrayList<PieEntry>()
        val operations = mainActivity.currentOperations.combineByCategoryExpenses()

        val colors = ArrayList<Int>()
        for(i in operations) {
            entries.add(PieEntry(-i.third.toFloat(), i.first.name))
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

    // Нажатие на элемент списка категорий
    override fun onItemClicked(item: Triple<Category, Int, Int>) {
        val mainActivity = activity as MainActivity
        mainActivity.setFragment(CategoryFragment(item.first, false))
    }

    @SuppressLint("SetTextI18n")
    override fun update() {
        dateTxt.text = "Последние ${mainActivity.period} дней"
        updateOperations()
    }
}