package com.example.uwasting.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.example.uwasting.R
import com.example.uwasting.activities.MainActivity

// Фргамент для хедера Navigation View
class NavigationHeaderFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_navigation_header, container, false)
        val mainActivity = activity as MainActivity

        val layout = view.findViewById<LinearLayout>(R.id.layout)

        layout.setOnClickListener {
            mainActivity.setFragment(AccountFragment())
        }

        return view
    }


}