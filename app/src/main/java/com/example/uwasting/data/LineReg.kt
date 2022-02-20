package com.example.uwasting.data

import android.util.Log
import kotlin.math.pow
import kotlin.math.sqrt
import kotlin.random.Random

class LineReg(private var income:ArrayList<Int>, private var expense:ArrayList<Int> ) {
    private var split = 0.6
    private val random = Random(1)
    init{
        while(income.size<expense.size)income.add(0)
        while(income.size>expense.size)expense.add(0)
    }

    fun evaluateAlgorithm(): Float {

        val trainTest = trainTestSplit(income, expense)
        val train = trainTest.first
        val test = trainTest.second
        val testSet = ArrayList<Pair<Int, Int>>()
        for (i in test) {
            val iCopy = Pair(i.first, i.second)
            testSet.add(iCopy)
        }
        val predicted = simpleLinearRegression(train, testSet)
        val actual = ArrayList<Int>()
        for (i in test) actual.add(i.second)
        return rmseMetric(actual, predicted)
    }

    private fun mean(values:ArrayList<Int>):Float{
        var sum =0
        for (i in values)sum+=i
        return sum.toFloat()/values.size
    }

    private fun covariance(income:ArrayList<Int>, incomeMean:Float,
                   expense:ArrayList<Int>, expenseMean:Float):Float{
        var covar = 0f
        for (i in 0 until income.size){
            covar+=(income[i]-incomeMean)*(expense[i]-expenseMean)
        }
        return covar
    }

    private fun variance(values: ArrayList<Int>, mean:Float):Float{
        var sum =0f
        for (i in values)
            sum+=(i-mean).pow(2)
        return sum
    }
    private fun coefficients(expense: ArrayList<Int>, income:ArrayList<Int>):Pair<Float, Float> {
        val incomeMean = mean(income)
        val expenseMean = mean(expense)

        val b1 = covariance(income, incomeMean, expense, expenseMean) / variance(expense, expenseMean)
        val b0 = incomeMean - b1*expenseMean
        return Pair(b0, b1)
    }
    private fun trainTestSplit(income:ArrayList<Int>, expense:ArrayList<Int>):
    Pair<ArrayList<Pair<Int,Int>>, ArrayList<Pair<Int,Int>>>{
        val expenseIncomeCopy = ArrayList<Pair<Int,Int>>()
        for (i in 0 until income.size){
            expenseIncomeCopy.add(Pair(expense[i], income[i]))
        }

        val train = ArrayList<Pair<Int,Int>>()
        val trainSize = split*income.size

        while (train.size<trainSize){
            val index  =random.nextInt(0, expenseIncomeCopy.size)
            train.add(expenseIncomeCopy[index])
            expenseIncomeCopy.removeAt(index)
        }
        return Pair(train, expenseIncomeCopy)
    }

    private fun rmseMetric(actual:ArrayList<Int>, predicted:ArrayList<Float>): Float {
        var sumError = 0f
        for (i in 0 until actual.size){
            val predictionError = predicted[i]-actual[i]
            sumError+=predictionError.pow(2)
        }
        val meanError = sumError/actual.size.toFloat()
        return sqrt(meanError)
    }
    private fun simpleLinearRegression(train:ArrayList<Pair<Int,Int>>, test:ArrayList<Pair<Int,Int>>):
    ArrayList<Float>{
        val predictions = ArrayList<Float>()
        val income = ArrayList<Int>()
        val expense = ArrayList<Int>()
        for (i in train){
            income.add(i.second)
            expense.add(i.first)
        }
        val b = coefficients(expense, income)
        val b0 = b.first
        val b1 = b.second
        Log.d("b0b1", b0.toString()+' '+b1.toString())
        for (i in test){
            val yHat = b0+b1*i.first
            predictions.add(yHat)
        }
        return predictions
    }
}