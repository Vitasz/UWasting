package com.example.uwasting.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.uwasting.R
import com.example.uwasting.activities.MainActivity

// Фрагмент с расходами
class ExpensesFragment : Fragment() {

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_expenses, container, false)
        val mainActivity = activity as MainActivity

        // Получение виджетов
        val categoriesList = view.findViewById<ConstraintLayout>(R.id.list_layout)
        val addExpenseBtn = view.findViewById<Button>(R.id.add_expense_btn)
        val totalExpensesTxt = view.findViewById<TextView>(R.id.totalExpenses)
        totalExpensesTxt.text = mainActivity.operations.GetTotalSumExpenses().toString()
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