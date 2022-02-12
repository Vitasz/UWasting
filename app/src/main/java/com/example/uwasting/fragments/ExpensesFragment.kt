package com.example.uwasting.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.uwasting.R
import com.example.uwasting.activities.MainActivity

// Фрагмент с расходами
class ExpensesFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_expenses, container, false)
        val mainActivity = activity as MainActivity

        // Получение виджетов
        val categoriesList = view.findViewById<ConstraintLayout>(R.id.list_layout)
        val addExpenseBtn = view.findViewById<Button>(R.id.add_expense_btn)

        // переход на фрагмент с категориями
        categoriesList.setOnClickListener {
            mainActivity.setFragment(CategoryFragment())
        }

        // Добавление расхода
        addExpenseBtn.setOnClickListener {
            mainActivity.setFragment(NewExpenseFragment())
        }

        return view
    }


}