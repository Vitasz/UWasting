package com.example.uwasting.fragments

import android.Manifest
import android.annotation.SuppressLint
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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.uwasting.R
import com.example.uwasting.activities.MainActivity
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
import com.google.android.material.button.MaterialButton
import org.apache.commons.csv.CSVFormat
import org.apache.commons.csv.CSVPrinter
import java.io.File
import java.nio.file.Files
import java.nio.file.Paths
import kotlin.math.pow
import kotlin.math.round

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
    override fun onItemClicked(item: Triple<Category, Int, Int>){
        mainActivity.setFragment(CategoryFragment(item.first, true))
    }
    @SuppressLint("SetTextI18n")
    fun updateOperations(){

        totalIncomesTxt.text = '+' + (round(mainActivity.currentOperations.GetTotalSumIncomes().toFloat()/mainActivity.ue*100) /100.0).toString()+mainActivity.curr
        //Список с категориями
        recyclerView.layoutManager = LinearLayoutManager(mainActivity)
        recyclerView.adapter = CategoryRecyclerView(mainActivity.currentOperations.CombineByCategoryIncomes(), this, mainActivity)
        //Диаграмма
        loadPieChartData()

        val sumIncomes = mainActivity.currentOperations.GetTotalSumIncomes()
        val sumExpenses = mainActivity.currentOperations.GetTotalSumExpenses()
        val balance = (sumIncomes + sumExpenses) / ((100.99 / 100).pow(mainActivity.Period / 30))

        balanceView.text = mainActivity.getString(R.string.balance) + " " + String.format("%.2f", balance)

    }
    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("SetTextI18n", "SdCardPath")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mainActivity = activity as MainActivity
        val view = inflater.inflate(R.layout.fragment_incomes, container, false)
        totalIncomesTxt = view.findViewById(R.id.sum_txt)
        val exportToCSVBtn = view.findViewById<Button>(R.id.export_btn)
        val periodLayout = view.findViewById<ConstraintLayout>(R.id.period_layout)
        val addIncomeBtn = view.findViewById<MaterialButton>(R.id.add_income_btn)
        balanceView = view.findViewById(R.id.balance_inc)
        dateTxt = view.findViewById(R.id.date_txt)
        dateTxt.text = "Последние ${mainActivity.Period} дней"
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
            mainActivity.checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, 123)
            exportToCSV()
        }

        return view
    }

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

    private fun loadPieChartData(){
        val entries = ArrayList<PieEntry>()
        val operations = mainActivity.currentOperations.CombineByCategoryIncomes()

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

    @RequiresApi(Build.VERSION_CODES.O)
    private fun exportToCSV(){
        val filename = "incomes.csv"
        val path = context?.getExternalFilesDir(null)
        val fileOut = File(path, filename)
        fileOut.delete()
        fileOut.createNewFile()
        val stringPath = path.toString()

        val operations = mainActivity.currentOperations.selectOperationsIncomes()
        val writer = Files.newBufferedWriter(Paths.get("$stringPath/$filename"))
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
    }

    @SuppressLint("SetTextI18n")
    override fun update() {
        dateTxt.text = "Последние ${mainActivity.Period} дней"
        updateOperations()
    }
}