package com.example.uwasting.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.example.uwasting.R
import com.example.uwasting.activities.MainActivity
import com.example.uwasting.activities.StartingActivity
import com.google.android.material.appbar.MaterialToolbar


class EmailFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_email, container, false)
        val startingActivity = activity as StartingActivity

        val toolbar = view.findViewById<MaterialToolbar>(R.id.toolbar)
        val nextBtn = view.findViewById<Button>(R.id.next_btn)
        val privacyPolicyTxt = view.findViewById<TextView>(R.id.pp_txt)
        val emailEdit = view.findViewById<EditText>(R.id.email_edit)

        nextBtn.setOnClickListener {
            if (emailEdit.text.toString() contentEquals "") {
                // TODO("ДОБАВИТЬ ОШИБКУ О ПУСТЫХ ПОЛЯХ")
            }
            else {
                startingActivity.user.email = emailEdit.text.toString() // Получаем мыло
                startingActivity.setFragment(NameFragment()) // Переход на страницу с вводом имени и фамилии
            }
        }

        toolbar.setNavigationOnClickListener {
            startingActivity.prevFragment()
        }
        return view
    }

}