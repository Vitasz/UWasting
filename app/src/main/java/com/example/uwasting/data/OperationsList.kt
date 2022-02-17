package com.example.uwasting.data

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList

class OperationsList(var list: List<Operation>) {


    fun GetTotalSumIncomes():Int{
        var tmp:Int = 0
        for(i in list) {
            if (i.amount>0)tmp+=i.amount
        }
        return tmp
    }
    fun GetTotalSumExpenses():Int{
        var tmp:Int = 0
        for(i in list) {
            if (i.amount<0)tmp+=i.amount
        }
        return tmp
    }
    @RequiresApi(Build.VERSION_CODES.O)
    fun SelectOperations(Period:Int):List<Operation>{
        val now = LocalDateTime.now()
        var tmp = ArrayList<Operation>()
        for (i in list) {
            Log.d("date", i.date)
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
            val t = i.date.replace('T', ' ')
            val date = LocalDateTime.parse(t, formatter)
            Log.d("date", date.toString())
            if (now.minusDays(Period.toLong())<date)tmp.add(i)
        }
        return tmp
    }
}