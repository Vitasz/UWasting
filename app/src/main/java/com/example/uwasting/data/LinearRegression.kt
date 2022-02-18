package com.example.uwasting.data
import kotlin.math.pow
import kotlin.math.sqrt

/*class SimpleLinearRegressionModel(independentVariables: IntArray, dependentVariables: DoubleArray) {
    private val meanX: Double = independentVariables.sum().div(independentVariables.count())
    private val meanY: Double = dependentVariables.sum().div(dependentVariables.count())
    private val variance: Double = independentVariables.stream().mapToDouble { (it - meanX).pow(2) }.sum()

    private fun covariance(independentVariables, dependentVariables): Double {
        var covariance = 0.0
        for (i in 0 until independentVariables.size) {
            val xPart = independentVariables[i] - meanX
            val yPart = dependentVariables[i] - meanY
            covariance += xPart * yPart
        }
        return covariance
    }

    private val b1 = covariance(independentVariables, dependentVariables).div(variance)
    private val b0 = meanY - b1 * meanX

    fun test(xTest: List<Double>, yTest: List<Double>) {
        var errorSum = 0.0
        var sst = 0.0
        var ssr = 0.0
        for (i in 0 until xTest.count()) {
            val x = xTest[i]
            val y = yTest[i]
            val yPred = predict(x)
            errorSum += (yPred - y).pow(2)
            sst += (y - meanY).pow(2)
            ssr += (y - yPred).pow(2)
        }
        println("RMSE = " + sqrt(errorSum.div(xTest.size)))
        println("R² = " + (1 - (ssr / sst)))
    }

    private fun predict(independentVariable: Double) = b0 + b1 * independentVariable
}*/