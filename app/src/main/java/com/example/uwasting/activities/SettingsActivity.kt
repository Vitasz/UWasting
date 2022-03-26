package com.example.uwasting.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

import com.example.uwasting.data.User
import com.example.uwasting.preferences.MyPreference

class SettingsActivity: AppCompatActivity() {
    lateinit var  myPreference: MyPreference
    lateinit var context: Context

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        context = this
        val arguments = intent.extras
        var language:String
        var user = User()
        arguments!!.let {
            language = it.getString("language", "en")

        }
        myPreference = MyPreference(this)
        myPreference.setLanguage(language)

        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}