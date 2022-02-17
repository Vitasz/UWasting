package com.example.uwasting.data

import com.google.gson.annotations.SerializedName
import java.util.*

class Operation (

    @SerializedName("Value")
    var amount: Int,
    @SerializedName("Category")
    var category: String,
    @SerializedName("Date")
    var date:Date,
    @SerializedName("Id")
    var id:Int
)