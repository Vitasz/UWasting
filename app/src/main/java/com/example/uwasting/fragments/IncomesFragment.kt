package com.example.uwasting.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.uwasting.R
import com.example.uwasting.activities.MainActivity
import com.example.uwasting.data.CategoryRecyclerView
import com.example.uwasting.dialogs.PeriodDialog
import com.google.android.material.button.MaterialButton

// Фрагмент с доходами
class IncomesFragment : Fragment() {


    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_incomes, container, false)
        val mainActivity = activity as MainActivity

        val periodLayout = view.findViewById<ConstraintLayout>(R.id.period_layout)
        val dateTxt = view.findViewById<TextView>(R.id.date_txt)
        val listLayout = view.findViewById<ConstraintLayout>(R.id.list_layout)
        val addIncomeBtn = view.findViewById<MaterialButton>(R.id.add_income_btn)
        val totalIncomesTxt = view.findViewById<TextView>(R.id.sum_txt)
        totalIncomesTxt.text = '+'+mainActivity.operations.GetTotalSumIncomes().toString()
        listLayout.setOnClickListener {
            mainActivity.setFragment(CategoryFragment())
        }

        val recyclerView = view.findViewById<RecyclerView>(R.id.categories_list)
        recyclerView.layoutManager = LinearLayoutManager(mainActivity)
        recyclerView.adapter = CategoryRecyclerView(mainActivity.operations.CombineByCategoryIncomes())

        // Нажатие на период
        periodLayout.setOnClickListener {
            val dialog = PeriodDialog()
            dialog.show(parentFragmentManager, "period")
        }

        addIncomeBtn.setOnClickListener {
            mainActivity.setFragment(NewIncomeFragment())
        }


        return view
    }

}