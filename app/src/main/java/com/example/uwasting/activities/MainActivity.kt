package com.example.uwasting.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.example.uwasting.R
import com.example.uwasting.fragments.TabFragment
import com.google.android.material.appbar.MaterialToolbar

// Главная активность
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setFragment(TabFragment())
    }

    // Переключение фрагмента
    fun setFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).
        addToBackStack(fragment.tag).commit()
    }

    // Предыдущий фрагмент
    fun prevFragment() {
        supportFragmentManager.popBackStack()
    }
}