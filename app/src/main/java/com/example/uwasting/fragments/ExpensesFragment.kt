package com.example.uwasting.fragments

import android.Manifest
import android.annotation.SuppressLint
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
import com.example.uwasting.data.Categories
import com.example.uwasting.data.Category

import com.example.uwasting.data.CategoryRecyclerView
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

// Фрагмент с расходами
class ExpensesFragment : Fragment(), OnItemClickListener, UpdateFragment {
    private lateinit var pieChart: PieChart
    private lateinit var mainActivity:MainActivity
    private lateinit var dateTxt:TextView
    private lateinit var recyclerView:RecyclerView
    private lateinit var totalExpensesTxt:TextView
    fun UpdateOperations(){
        loadPieChartData()
        totalExpensesTxt.text = mainActivity.currentOperations.GetTotalSumExpenses().toString()+"$"


        recyclerView.layoutManager = LinearLayoutManager(mainActivity)
        recyclerView.adapter = CategoryRecyclerView(mainActivity.currentOperations.CombineByCategoryExpenses(), this)
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
        recyclerView = view.findViewById<RecyclerView>(R.id.categories_list)
        dateTxt = view.findViewById(R.id.date_txt)
        dateTxt.text = "Последние ${mainActivity.Period} дней"
        totalExpensesTxt = view.findViewById<TextView>(R.id.totalExpenses)
        pieChart = view.findViewById(R.id.diagram_expenses)
        setupPieChart()
        // Нажатие на период
        periodLayout.setOnClickListener {
            val dialog = PeriodDialog(mainActivity, this)
            dialog.show(parentFragmentManager, "period")
        }

        UpdateOperations()
        // Добавление расхода
        addExpenseBtn.setOnClickListener {
            mainActivity.setFragment(NewExpenseFragment())
            /*val operationsPerMonth = mainActivity.operations
            val count = operationsPerMonth.list[0]
            val sumOperations = operationsPerMonth.list[1]
            val model = SimpleLinearRegressionModel(count, sumOperations)*/
        }

        exportToCSVBtn.setOnClickListener {
            mainActivity.checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, 123)

            val filename = "expenses.csv"
            var path = context?.getExternalFilesDir(null)
            var fileOut = File(path, filename)
            fileOut.delete()
            fileOut.createNewFile()
            val stringPath = path.toString()

            val operations = mainActivity.currentOperations
            val writer = Files.newBufferedWriter(Paths.get("$stringPath/$filename"))
            val csvPrinter = CSVPrinter(writer, CSVFormat.DEFAULT
                .withHeader("OperationId", "Category", "Amount", "Date"))

            for (operation in operations.list) {
                val operationData = listOf(
                    operation.id,
                    operation.category,
                    operation.amount,
                    operation.date)

                csvPrinter.printRecord(operationData)
            }

            csvPrinter.flush()
            csvPrinter.close()

        }

        return view
    }

    private fun setupPieChart() {
        pieChart.isDrawHoleEnabled = false
        pieChart.setUsePercentValues(true)


        /*pieChart.setEntryLabelTextSize(12F)
        pieChart.setEntryLabelColor(Color.BLACK)*/
        pieChart.setDrawEntryLabels(false)
        pieChart.centerText = ""
        pieChart.description.isEnabled = false

        var l = pieChart.legend
        l.verticalAlignment = Legend.LegendVerticalAlignment.TOP
        l.horizontalAlignment = Legend.LegendHorizontalAlignment.RIGHT
        l.orientation = Legend.LegendOrientation.VERTICAL
        l.setDrawInside(false)
        l.isEnabled = false
    }

    private fun loadPieChartData(){
        val mainActivity = activity as MainActivity
        var entries = ArrayList<PieEntry>()
        var sum = 0
        var operations = mainActivity.currentOperations.CombineByCategoryExpenses()

        val colors = ArrayList<Int>()
        for(i in operations) {
            entries.add(PieEntry(-i.third.toFloat(), i.first.name))
            colors.add(i.first.color)
        }

        var dataSet = PieDataSet(entries, "")
        dataSet.colors = colors

        var data = PieData(dataSet)
        data.setDrawValues(false)

        pieChart.data = data
        pieChart.invalidate()
        pieChart.animateY(1000, Easing.EaseInOutQuad)

    }

    override fun onItemClicked(item: Triple<Category, Int, Int>) {
        val mainActivity = activity as MainActivity
        mainActivity.setFragment(CategoryFragment(item.first, false))
    }

    @SuppressLint("SetTextI18n")
    override fun update() {
        dateTxt.text = "Последние ${mainActivity.Period} дней"
        UpdateOperations()
    }
}