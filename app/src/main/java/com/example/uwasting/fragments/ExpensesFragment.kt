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

import com.example.uwasting.data.CategoryRecyclerView
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
class ExpensesFragment : Fragment() {
    private lateinit var pieChart: PieChart

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_expenses, container, false)
        val mainActivity = activity as MainActivity

        // Получение виджетов
        val exportToCSVBtn = view.findViewById<Button>(R.id.export_btn)
        val categoriesList = view.findViewById<ConstraintLayout>(R.id.list_layout)
        val addExpenseBtn = view.findViewById<Button>(R.id.add_expense_btn)
        val totalExpensesTxt = view.findViewById<TextView>(R.id.totalExpenses)
        totalExpensesTxt.text = mainActivity.currentOperations.getTotalSumExpenses().toString()

        pieChart = view.findViewById(R.id.diagram_expenses)
        setupPieChart()
        loadPieChartData()

        // переход на фрагмент с категориями
        categoriesList.setOnClickListener {
            mainActivity.setFragment(CategoryFragment())
        }
        //Список с категориями
        val recyclerView = view.findViewById<RecyclerView>(R.id.categories_list)
        recyclerView.layoutManager = LinearLayoutManager(mainActivity)
        recyclerView.adapter = CategoryRecyclerView(mainActivity.currentOperations.combineByCategoryExpenses())
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

        var legend = pieChart.legend
        legend.verticalAlignment = Legend.LegendVerticalAlignment.TOP
        legend.horizontalAlignment = Legend.LegendHorizontalAlignment.RIGHT
        legend.orientation = Legend.LegendOrientation.VERTICAL
        legend.setDrawInside(false)
        legend.isEnabled = false
    }

    private fun loadPieChartData(){
        val mainActivity = activity as MainActivity
        var entries = ArrayList<PieEntry>()
        var sum = 0
        var operations = mainActivity.currentOperations.combineByCategoryExpenses()

        val colors = ArrayList<Int>()
        for(i in operations) {
            entries.add(PieEntry(-i.third.toFloat(), i.first.name))
            colors.add(i.first.color)
        }

        /*for(color in ColorTemplate.MATERIAL_COLORS) {
            colors.add(color)
        }

        for(color in ColorTemplate.VORDIPLOM_COLORS) {
            colors.add(color)
        }*/

        var dataSet = PieDataSet(entries, "")
        dataSet.colors = colors

        var data = PieData(dataSet)
        data.setDrawValues(false)

        pieChart.data = data
        pieChart.invalidate()
        pieChart.animateY(1000, Easing.EaseInOutQuad)

    }
}