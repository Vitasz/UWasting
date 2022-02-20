package com.example.uwasting.data.remote

import com.google.gson.annotations.SerializedName

// Получения данных об ИПЦ с сайта www.statbureau.org
class StatBureauData(
    @SerializedName("InflationRate")
    var inflationRate: Float,
    @SerializedName("InflationRateRounded")
    var inflationRateRounded: Float,
    @SerializedName("InflationRateFormatted")
    var inflationRateFormatted: String,
    @SerializedName("Month")
    var month: String,
    @SerializedName("MonthFormatted")
    var monthFormatted: String,
    @SerializedName("Country")
    var country: Int
) {

}