package com.example.uwasting.data

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList

class OperationsList(var item: List<Operation>) {
    var list = item


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
    fun CombineByCategoryIncomes():ArrayList<Triple<Category, Int,Int>>{
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
    fun CombineByCategoryExpenses():ArrayList<Triple<Category, Int, Int>>{
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
    fun SortByDate():ArrayList<Triple<LocalDate, Category, Int>>{
        val res = ArrayList<Triple<LocalDate, Category, Int>>()
        val categories = Categories()
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH)

        for (i in list) {
            Log.d("TAG",i.date)
            res.add(
                Triple(
                    LocalDate.parse(i.date.substring(0, 10), formatter),
                    categories.hasInCommon(i.category),
                    i.amount
                )
            )
        }
        //Log.d("TAG",res[0].first.toString())

        return ArrayList(res.sortedBy { it.first })
    }
}