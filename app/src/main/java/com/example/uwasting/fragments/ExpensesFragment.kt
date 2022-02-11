package com.example.uwasting.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

        val categoriesList = view.findViewById<ConstraintLayout>(R.id.list_layout)
        categoriesList.setOnClickListener {
            mainActivity.setFragment(CategoryFragment())
        }
        return view
    }


}