package com.example.uwasting.fragments

import android.Manifest
import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.constraintlayout.widget.ConstraintLayout

import androidx.fragment.app.Fragment
import com.example.uwasting.R
import com.example.uwasting.activities.MainActivity

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.uwasting.data.*

import com.example.uwasting.dialogs.PeriodDialog
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.utils.ColorTemplate
import com.google.android.material.button.MaterialButton
import org.apache.commons.csv.CSVFormat
import org.apache.commons.csv.CSVPrinter
import java.io.File
import java.nio.file.Files
import java.nio.file.Paths
interface UpdateFragment{
    fun update()
}
// Фрагмент с доходами
class IncomesFragment : Fragment(), OnItemClickListener, UpdateFragment {
    private lateinit var pieChart: PieChart
    private lateinit var totalIncomesTxt:TextView
    private lateinit var recyclerView:RecyclerView
    private lateinit var dateTxt: TextView
    override fun onItemClicked(item: Triple<Category, Int, Int>){
        val mainActivity = activity as MainActivity
        mainActivity.setFragment(CategoryFragment(item.first, true))
    }
    @SuppressLint("SetTextI18n")
    fun UpdateOperations(){
        val mainActivity = activity as MainActivity

        totalIncomesTxt.text = '+' + mainActivity.currentOperations.GetTotalSumIncomes().toString()+"$"
        //Список с категориями
        recyclerView.layoutManager = LinearLayoutManager(mainActivity)
        recyclerView.adapter = CategoryRecyclerView(mainActivity.currentOperations.CombineByCategoryIncomes(), this)
        //Диаграмма
        loadPieChartData()

    }
    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("SetTextI18n", "SdCardPath")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val mainActivity = activity as MainActivity
        val view = inflater.inflate(R.layout.fragment_incomes, container, false)
        totalIncomesTxt = view.findViewById<TextView>(R.id.sum_txt)
        val exportToCSVBtn = view.findViewById<Button>(R.id.export_btn)
        val periodLayout = view.findViewById<ConstraintLayout>(R.id.period_layout)
        val addIncomeBtn = view.findViewById<MaterialButton>(R.id.add_income_btn)
        dateTxt = view.findViewById<TextView>(R.id.date_txt)
        dateTxt.text = "Последние ${mainActivity.Period} дней"
        recyclerView = view.findViewById<RecyclerView>(R.id.categories_list)
        pieChart = view.findViewById(R.id.diagram_incomes)
        setupPieChart()
        UpdateOperations()

        // Нажатие на период
        periodLayout.setOnClickListener {
            val dialog = PeriodDialog(mainActivity, this)
            dialog.show(parentFragmentManager, "period")
        }

        addIncomeBtn.setOnClickListener {
            mainActivity.setFragment(NewIncomeFragment())
        }


        exportToCSVBtn.setOnClickListener {
            mainActivity.checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, 123)
            ExportToCSV()
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
        var operations = mainActivity.currentOperations.CombineByCategoryIncomes()

        val colors = ArrayList<Int>()
        for(i in operations) {
            entries.add(PieEntry(i.third.toFloat(), i.first.name))
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
    @RequiresApi(Build.VERSION_CODES.O)
    private fun ExportToCSV(){
        val mainActivity = activity as MainActivity
        val filename = "incomes.csv"
        val path = context?.getExternalFilesDir(null)
        val fileOut = File(path, filename)
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

    override fun update() {
        val mainActivity=activity as MainActivity
        dateTxt.text = "Последние ${mainActivity.Period} дней"
        UpdateOperations()
    }
}