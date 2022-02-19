package com.example.uwasting.fragments

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.uwasting.R
import com.example.uwasting.activities.MainActivity
import com.example.uwasting.data.*
import com.example.uwasting.dialogs.OperationDialog
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.utils.ColorTemplate
import com.google.android.material.appbar.MaterialToolbar
import java.lang.Math.abs
import java.time.LocalDate

interface onSetBaseOperation{
    fun onSet()
}

class valueFormatter(private val xValsDateLabel: ArrayList<LocalDate>) : ValueFormatter() {

    override fun getFormattedValue(value: Float): String {
        return value.toString()
    }

    override fun getAxisLabel(value: Float, axis: AxisBase): String {
        if (value.toInt() >= 0 && value.toInt() <= xValsDateLabel.size - 1) {
            return xValsDateLabel[value.toInt()].toString()
        } else {
            return ("").toString()
        }
    }
}

// Фрагмент с выбранной категорией
class CategoryFragment(private var category: Category, private var income:Boolean) : Fragment(), OnOperationClickListener, onSetBaseOperation {
    lateinit var barChart: BarChart
    lateinit var listOperations:OperationsList
    lateinit var recyclerView:RecyclerView
    lateinit var mainActivity: MainActivity
    @RequiresApi(Build.VERSION_CODES.O)
    fun setValues(){
        recyclerView.adapter = OperationsRecyclerView(listOperations.SortByDate(), this, mainActivity)
        val barDataSet = BarDataSet(getEntries(listOperations), "")
        barDataSet.setColors(listOf(category.color));
        val barData = BarData(barDataSet);
        barData.barWidth=0.5f
        barChart.setData(barData);
    }
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_category, container, false)
        mainActivity = activity as MainActivity

        val toolbar = view.findViewById<MaterialToolbar>(R.id.toolbar)
        listOperations = mainActivity.currentOperations.SelectByCategory(category)
        if (income)listOperations=OperationsList(listOperations.selectOperationsIncomes())
        else listOperations=OperationsList(listOperations.selectOperationsExpenses())
        recyclerView = view.findViewById(R.id.operations_list)
        recyclerView.layoutManager = LinearLayoutManager(mainActivity)


        //Диаграмма
        barChart = view.findViewById(R.id.operations_barchart);
        //Настройка диаграммы
        barChart.xAxis.granularity = 1f
        barChart.getDescription().setEnabled(false);
        barChart.xAxis.valueFormatter = valueFormatter(ArrayList(listOperations.CombineByDateIncomesAndExpenses().keys))
        barChart.legend.isEnabled = false
        barChart.animateY( 300)
        //Загрузка данных
        setValues()


        // Нажатие на кнопку "Назад"
        toolbar.setNavigationOnClickListener {
            mainActivity.prevFragment()
        }

        return view
    }
    @RequiresApi(Build.VERSION_CODES.O)
    fun getEntries(list:OperationsList):ArrayList<BarEntry>{
        val tmp = list.CombineByDateIncomesAndExpenses()
        val dataVals = ArrayList<BarEntry>()
        var cnt=0f
        for (i in tmp){
            if (income)
                dataVals.add(BarEntry(cnt, i.value.first.toFloat()))
            else
                dataVals.add(BarEntry(cnt, kotlin.math.abs(i.value.second.toFloat())))
            cnt+=1
        }
        return dataVals
    }
    //Нажатие на операцию
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onItemClick(item: Triple<LocalDate, Category, Int>) {
        val mainActivity = activity as MainActivity
        Log.d("Operation id:", mainActivity.currentOperations.findOperation(item.first.toString(), item.third, item.second).toString())
        val operationDialog = context?.let { it1 ->
                OperationDialog(
                    it1,
                    mainActivity.currentOperations.findOperation(
                        item.first.toString(),
                        item.third,
                        item.second
                    ),
                    item.third,
                    mainActivity,
                    this
                )
        }

        operationDialog?.show()
    }

    override fun onSet() {
        setValues()
    }

}