package com.example.uwasting.data

import android.os.Build
import android.os.Parcel
import android.os.Parcelable
import androidx.annotation.RequiresApi
import com.google.gson.annotations.SerializedName
import java.sql.Date
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class Operation (

    @SerializedName("Value")
    var amount: Int,
    @SerializedName("Category")
    var category: String,
    @SerializedName("date" )
    var date: LocalDate,
    @SerializedName("Id")
    var id:Int
)