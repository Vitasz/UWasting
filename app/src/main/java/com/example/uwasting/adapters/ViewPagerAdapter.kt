package com.example.uwasting.adapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.uwasting.fragments.ExpensesFragment
import com.example.uwasting.fragments.IncomesFragment


// Адаптер для переключения между доходами/расходами
class ViewPagerAdapter(fragment: Fragment): FragmentStateAdapter(fragment) {
    private val numPages = 2

    // Отображение текущего фрагмента
    override fun createFragment(position: Int): Fragment {
        when (position) {
            0 -> return IncomesFragment()
            1 -> return ExpensesFragment()
        }
        return IncomesFragment()
    }

// Полчуение количества страниц
    override fun getItemCount(): Int {
        return numPages
    }


}