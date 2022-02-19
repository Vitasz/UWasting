package com.example.uwasting.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.uwasting.R
import com.example.uwasting.activities.MainActivity
import com.example.uwasting.data.Categories
import com.example.uwasting.data.SelectingCategoryRecyclerView
import com.google.android.material.appbar.MaterialToolbar

// Фрагмент выбора категории
class SelectCategoryFragment(private var setCategory: SetCategory) : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_select_category, container, false)
        val mainActivity = activity as MainActivity
        // Получение виджетов
        val toolbar = view.findViewById<MaterialToolbar>(R.id.toolbar)
        var categoriesList = view.findViewById<RecyclerView>(R.id.categories_list)
        val selectBtn = view.findViewById<Button>(R.id.select_btn)
        val adapter = SelectingCategoryRecyclerView()
        categoriesList.adapter = adapter
        categoriesList.layoutManager = LinearLayoutManager(mainActivity)
        selectBtn.setOnClickListener{
            Log.d("tag", adapter.selectedCategoryName)
            setCategory.setCategory(adapter.selectedCategoryName)
            mainActivity.prevFragment()
        }
        val categories = Categories()
        for (category in categories.common)
            adapter.addItem(category)
        adapter.addItem(categories.other)

        // Перемещение на предыдущий фрагмент
        toolbar.setNavigationOnClickListener {
            mainActivity.prevFragment()
        }

        return view
    }


}