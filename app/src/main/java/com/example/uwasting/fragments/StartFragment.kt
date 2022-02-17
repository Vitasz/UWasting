package com.example.uwasting.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.uwasting.R
import com.example.uwasting.activities.StartingActivity


class StartFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_start, container, false)

        val startingActivity: StartingActivity = activity as StartingActivity

        val signInBtn = view.findViewById<Button>(R.id.sign_in_btn)
        val signUpBtn = view.findViewById<Button>(R.id.sign_up_btn)
        signInBtn.setOnClickListener {
            startingActivity.setFragment(SignInFragment())
        }
        signUpBtn.setOnClickListener {
            startingActivity.setFragment(EmailFragment())
        }

        return view
    }

}