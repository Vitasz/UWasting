package com.example.uwasting.data

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import org.nield.kotlinstatistics.*
import java.sql.Date
import java.time.LocalDate
import java.time.LocalDate.now
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.exp

// Линейная регрессия
class LineReg(private var expenses:ArrayList<Operation> ){
    @RequiresApi(Build.VERSION_CODES.O)
    fun evaluateAlgorithm():Double {

        val dateAmount: MutableMap<Long,Int> =mutableMapOf()
        for(i in expenses) {

            val nowDay = i.date.toEpochDay()
            if (!dateAmount.containsKey(nowDay)){
                dateAmount[nowDay] = i.amount
            }
            else{
                dateAmount[nowDay] = dateAmount[nowDay]!!+i.amount
            }
        }
        val tmp = ArrayList<Pair<Long,Int>>()
        for (i in dateAmount) tmp.add(Pair(i.key, i.value))
        if (tmp.size == 0)return 0.0
        if (tmp.size == 1) return tmp[0].second.toDouble() * 30
        val r = tmp.simpleRegression(
            xSelector = { it.first},
            ySelector = {it.second}
        )
        var sum = 0.0
        for (i in 0..30)

            sum+= r.predict((now()).toEpochDay().toDouble()+i)
        return sum
    }
}