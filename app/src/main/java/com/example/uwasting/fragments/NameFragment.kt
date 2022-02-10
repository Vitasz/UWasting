package com.example.uwasting.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import com.example.uwasting.R
import com.example.uwasting.activities.StartingActivity
import com.google.android.material.appbar.MaterialToolbar


class NameFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_name, container, false)
        val startingActivity = activity as StartingActivity
        val nameEdit = view.findViewById<EditText>(R.id.name_edit) // Имя
        val surnameEdit = view.findViewById<EditText>(R.id.surname_edit) // Фамилия
        val nextBtn = view.findViewById<Button>(R.id.next_btn)
        val toolbar = view.findViewById<MaterialToolbar>(R.id.toolbar)

        nextBtn.setOnClickListener {
            if ((nameEdit.text.toString() contentEquals "") or (surnameEdit.text.toString() contentEquals "")) {
                // TODO("ДОБАВИТЬ ОШИБКУ О ПУСТЫХ ПОЛЯХ")
            }
            else {
                startingActivity.user.name = nameEdit.text.toString() // Получаем имя
                startingActivity.user.surname = surnameEdit.text.toString() // Получаем фамилию
                startingActivity.setFragment(PasswordFragment()) // Перехдим к странице с вводом пароля
            }
        }

        toolbar.setNavigationOnClickListener {
            startingActivity.prevFragment()
        }

        return view
    }


}