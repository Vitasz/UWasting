package com.example.uwasting.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.uwasting.R
import com.example.uwasting.activities.MainActivity
import com.example.uwasting.activities.StartingActivity


class SignInFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_sign_in, container, false)
        val startingActivity = activity as StartingActivity

        val signInBtn = view.findViewById<Button>(R.id.sign_in_btn)

        signInBtn.setOnClickListener {
            val intent = Intent(startingActivity, MainActivity::class.java)
            startingActivity.startActivity(intent)
        }
        return view
    }

}