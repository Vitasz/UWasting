package com.example.uwasting.data

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList

class OperationsList(var item: ArrayList<Operation>) {
    var list = item


    fun getTotalSumIncomes():Int{
        var tmp:Int = 0
        for(i in list) {
            if (i.amount>0)tmp+=i.amount
        }
        return tmp
    }

    fun getTotalSumExpenses():Int{
        var tmp:Int = 0
        for(i in list) {
            if (i.amount<0)tmp+=i.amount
        }
        return tmp
    }

    fun combineByCategoryIncomes():ArrayList<Triple<Category, Int,Int>>{
        val res = ArrayList<Triple<Category, Int,Int>>()
        val tmp: MutableMap<String, Pair<Int, Int>> = mutableMapOf()
        for(i in list){
            if (i.amount>0) {
                if (!tmp.containsKey(i.category))
                    tmp[i.category] = Pair(1, i.amount)
                else {
                    tmp[i.category] = Pair(tmp[i.category]!!.first + 1, tmp[i.category]!!.second+i.amount)
                }
            }
        }
        val categories = Categories()
        for (i in tmp){
            res.add(Triple(categories.hasInCommon(i.key), i.value.first, i.value.second))
        }
        return ArrayList((res.sortedBy{it.third.toInt()}).reversed())
    }

    fun combineByCategoryExpenses():ArrayList<Triple<Category, Int, Int>>{
        val res = ArrayList<Triple<Category, Int, Int>>()
        val tmp: MutableMap<String, Pair<Int, Int>> = mutableMapOf()
        for(i in list){
            if (i.amount<0) {
                if (!tmp.containsKey(i.category))
                    tmp[i.category] = Pair(1, i.amount)
                else
                    tmp[i.category] = Pair(tmp[i.category]!!.first + 1, tmp[i.category]!!.second+i.amount)
            }
        }
        val categories = Categories()
        for (i in tmp){
            res.add(Triple(categories.hasInCommon(i.key), i.value.first, i.value.second))
        }
        return ArrayList(res.sortedBy{it.third})
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun sortByDate():ArrayList<Triple<LocalDate, Category, Int>>{
        val res = ArrayList<Triple<LocalDate, Category, Int>>()
        val categories = Categories()
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH)

        for (i in list) {
            res.add(
                Triple(
                    LocalDate.parse(i.date.substring(0, 10), formatter),
                    categories.hasInCommon(i.category),
                    i.amount
                )
            )
        }

        return ArrayList(res.sortedBy { it.first })
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun selectOperations(Period:Int):List<Operation>{
        val now = LocalDateTime.now()
        var tmp = ArrayList<Operation>()
        for (i in list) {
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
            val t = i.date.replace('T', ' ')
            val date = LocalDateTime.parse(t, formatter)
            if (now.minusDays(Period.toLong())<date)tmp.add(i)
        }
        return tmp
    }
}