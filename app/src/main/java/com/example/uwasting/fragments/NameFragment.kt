package com.example.uwasting.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.uwasting.R
import com.example.uwasting.activities.MainActivity
import com.google.android.material.appbar.MaterialToolbar


class NameFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_name, container, false)
        val mainActivity = activity as MainActivity

        val nextBtn = view.findViewById<Button>(R.id.next_btn)
        val toolbar = view.findViewById<MaterialToolbar>(R.id.toolbar)

        nextBtn.setOnClickListener {
            mainActivity.setFragment(PasswordFragment())
        }

        toolbar.setNavigationOnClickListener {
            mainActivity.prevFragment()
        }

        return view
    }


}