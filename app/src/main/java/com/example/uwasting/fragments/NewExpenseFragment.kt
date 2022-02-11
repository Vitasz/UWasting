package com.example.uwasting.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.uwasting.R
import com.example.uwasting.activities.MainActivity
import com.google.android.material.appbar.MaterialToolbar


class NewExpenseFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_new_income, container, false)
        val mainActivity = activity as MainActivity

        val toolbar = view.findViewById<MaterialToolbar>(R.id.toolbar)

        toolbar.setNavigationOnClickListener {
            mainActivity.prevFragment()
        }

        return view
    }

}