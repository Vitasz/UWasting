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
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH)
    @RequiresApi(Build.VERSION_CODES.O)
    public fun evaluateAlgorithm():Double {
        if (expenses.size == 0)return 0.0
        if (expenses.size == 1) return expenses[0].amount.toDouble() * 30

        val r = expenses.simpleRegression(
            xSelector = { (LocalDate.parse(it.date.substring(0, 10), formatter)).dayOfYear},
            ySelector = {it.amount}
        )
        var sum = 0.0
        for (i in 0..30)
            sum+= r.predict((now()).dayOfYear.toDouble())
        return sum
    }
}