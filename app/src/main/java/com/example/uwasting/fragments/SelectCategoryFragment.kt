package com.example.uwasting.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.example.uwasting.R
import com.example.uwasting.activities.MainActivity
import com.google.android.material.appbar.MaterialToolbar

// Фрагмент выбора категории
class SelectCategoryFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_select_category, container, false)
        val mainActivity = activity as MainActivity
        // Получение виджетов
        val addBtn = view.findViewById<TextView>(R.id.add_btn)
        val toolbar = view.findViewById<MaterialToolbar>(R.id.toolbar)

        // Добавление категории
        addBtn.setOnClickListener {
            mainActivity.setFragment(NewCategoryFragment())
        }

        // Перемещение на предыдущий фрагмент
        toolbar.setOnClickListener {
            mainActivity.prevFragment()
        }

        return view
    }


}