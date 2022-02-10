package com.example.uwasting.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.uwasting.R
import com.example.uwasting.activities.StartingActivity
import com.google.android.material.appbar.MaterialToolbar


class VerifyEmailFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_verify_email, container, false)
        val startingActivity = activity as StartingActivity

        val toolbar = view.findViewById<MaterialToolbar>(R.id.toolbar)
        val nextBtn = view.findViewById<Button>(R.id.next_btn)

        nextBtn.setOnClickListener {
            startingActivity.setFragment(VerifyEmailFragment())
        }

        toolbar.setNavigationOnClickListener {
            startingActivity.prevFragment()
        }

        return view
    }


}