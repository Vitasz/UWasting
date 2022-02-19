package com.example.uwasting.fragments

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.viewpager2.widget.ViewPager2
import com.example.uwasting.R
import com.example.uwasting.activities.MainActivity
import com.example.uwasting.activities.StartingActivity
import android.widget.TextView
import com.example.uwasting.adapters.ViewPagerAdapter
import com.example.uwasting.data.Constants
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.navigation.NavigationView
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

// Фрагмент с переключением доходов/расходов
class TabFragment : Fragment() {

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_tab, container, false)
        val mainActivity = activity as MainActivity

        // Получение виджетов
        val viewPager = view.findViewById<ViewPager2>(R.id.view_pager)
        val tabLayout = view.findViewById<TabLayout>(R.id.tab_layout)
        val toolbar = view.findViewById<MaterialToolbar>(R.id.toolbar)
        val drawerLayout = view.findViewById<DrawerLayout>(R.id.drawerLayout)
        val navigationView = view.findViewById<NavigationView>(R.id.navigation_view)
        val nameTxt = navigationView.getHeaderView(0).findViewById<TextView>(R.id.name_txt)
        val emailTxt = navigationView.getHeaderView(0).findViewById<TextView>(R.id.email_txt)

        // Нажатие на кнопку "Меню"
        toolbar.setNavigationOnClickListener {
            drawerLayout.openDrawer(GravityCompat.START)
        }

        navigationView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.change_email -> mainActivity.setFragment(VerifyPasswordFragment(Constants.CHANGE_EMAIl))
                R.id.change_password -> mainActivity.setFragment(VerifyPasswordFragment(Constants.CHANGE_PASSWORD))
                // Выход из аккаунта
                R.id.sign_out -> {
                    val intent = Intent(mainActivity, StartingActivity::class.java)
                    startActivity(intent)
                }
            }
            true
        }

        nameTxt.text = mainActivity.user.name+' '+mainActivity.user.surname
        emailTxt.text = mainActivity.user.email
        // Переход на фрагмент аккаунта
        navigationView.getHeaderView(0).setOnClickListener {
            mainActivity.setFragment(AccountFragment())
        }

        // Инициализация адаптера для переключателя
        val viewPagerAdapter = ViewPagerAdapter(this)
        viewPager.adapter = viewPagerAdapter

        // Заголовки переключателя
        val tabLayoutMediator = TabLayoutMediator(tabLayout, viewPager) { tab: TabLayout.Tab, position: Int ->
            when (position) {
                0 -> tab.text = getString(R.string.incomes)
                1 -> tab.text = getString(R.string.expenses)
            }
        }
        tabLayoutMediator.attach()


        return view
    }

}