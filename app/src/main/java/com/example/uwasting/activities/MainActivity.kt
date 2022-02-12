package com.example.uwasting.activities

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.example.uwasting.R
import com.example.uwasting.data.User
import com.example.uwasting.fragments.TabFragment
import com.google.android.material.appbar.MaterialToolbar

class MainActivity : AppCompatActivity() {
    val user: User = User()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val arguments = intent.extras
        arguments!!.let {
            user.name = it.getString("UserName", "NOT FOUND")
            user.surname = it.getString("UserSurName", "NOT FOUND")
            user.email = it.getString("UserEmail", "NOT FOUND")
            user.id = it.getInt("UserId", -1)
        }


        val toolbar = findViewById<MaterialToolbar>(R.id.toolbar)
        val drawerLayout = findViewById<DrawerLayout>(R.id.drawerLayout)

        toolbar.setNavigationOnClickListener {
            drawerLayout.openDrawer(GravityCompat.START)
        }

        setFragment(TabFragment())
    }

    fun setFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).
        addToBackStack(fragment.tag).commit()
    }

    fun prevFragment() {
        supportFragmentManager.popBackStack()
    }
}