package com.example.uwasting.data

import com.google.gson.annotations.SerializedName
import java.util.*

class Operation (

    @SerializedName("Value")
    var amount: Int,
    @SerializedName("Category")
    var category: String,
    @SerializedName("date")
    var date:String,
    @SerializedName("Id")
    var id:Int
)