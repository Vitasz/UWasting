package com.example.uwasting.data

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

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
        var res = ArrayList<Triple<Category, Int,Int>>()
        var tmp: MutableMap<String, Pair<Int, Int>> = mutableMapOf()
        for(i in list){
            if (i.amount>0) {
                if (!tmp.containsKey(i.category))
                    tmp[i.category] = Pair(1, i.amount)
                else {
                    tmp[i.category] = Pair(tmp[i.category]!!.first + 1, tmp[i.category]!!.second+i.amount)
                }
            }
        }
        var categories = Categories()
        for (i in tmp){
            res.add(Triple(categories.hasInCommon(i.key), i.value.first, i.value.second))
        }
        return ArrayList((res.sortedBy{it.third.toInt()}).reversed())
    }
    fun CombineByCategoryExpenses():ArrayList<Triple<Category, Int, Int>>{
        var res = ArrayList<Triple<Category, Int, Int>>()
        var tmp: MutableMap<String, Pair<Int, Int>> = mutableMapOf()
        for(i in list){
            if (i.amount<0) {
                if (!tmp.containsKey(i.category))
                    tmp[i.category] = Pair(1, i.amount)
                else
                    tmp[i.category] = Pair(tmp[i.category]!!.first + 1, tmp[i.category]!!.second+i.amount)
            }
        }
        var categories = Categories()
        for (i in tmp){
            res.add(Triple(categories.hasInCommon(i.key), i.value.first, i.value.second))
        }
        return ArrayList(res.sortedBy{it.third})
    }
}