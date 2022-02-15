package com.example.uwasting.data

import android.util.Log
import kotlin.properties.Delegates
class User  {
    lateinit var email: String;
    lateinit var password: String;
    lateinit var name: String;
    lateinit var surname: String;
    var id: Int = 0;
}