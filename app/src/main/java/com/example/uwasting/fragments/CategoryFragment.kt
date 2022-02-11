package com.example.uwasting.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.uwasting.R
import com.example.uwasting.activities.MainActivity
import com.example.uwasting.dialogs.OperationDialog
import com.google.android.material.appbar.MaterialToolbar

class CategoryFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_category, container, false)
        val mainActivity = activity as MainActivity

        val toolbar = view.findViewById<MaterialToolbar>(R.id.toolbar)
        var listLayout = view.findViewById<ConstraintLayout>(R.id.list_layout)

        listLayout.setOnClickListener {
            val operationDialog = context?.let { it1 -> OperationDialog(it1) }
            operationDialog?.show()
        }

        toolbar.setNavigationOnClickListener {
            mainActivity.prevFragment()
        }

        return view
    }

}