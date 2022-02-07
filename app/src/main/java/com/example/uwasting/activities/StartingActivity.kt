package com.example.uwasting.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.uwasting.R
import com.example.uwasting.fragments.StartFragment

class StartingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)

        setFragment(StartFragment())
    }

    fun setFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).
                    addToBackStack(fragment.tag).commit()
    }

    fun prevFragment() {
        supportFragmentManager.popBackStack()
    }

}