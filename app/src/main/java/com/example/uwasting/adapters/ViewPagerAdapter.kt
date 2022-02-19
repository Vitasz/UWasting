package com.example.uwasting.adapters

import androidx.fragment.app.Fragment
import androidx.viewpager.widget.PagerAdapter.POSITION_NONE
import androidx.viewpager.widget.PagerAdapter.POSITION_UNCHANGED
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.uwasting.fragments.ExpensesFragment
import com.example.uwasting.fragments.IncomesFragment


// Адаптер для переключения между доходами/расходами
class ViewPagerAdapter(fragment: Fragment): FragmentStateAdapter(fragment) {
    private val NUM_PAGES = 2;

    override fun createFragment(position: Int): Fragment {
        when (position) {
            0 -> return IncomesFragment()
            1 -> return ExpensesFragment()
        }
        return IncomesFragment()
    }

    override fun getItemCount(): Int {
        return NUM_PAGES
    }


}