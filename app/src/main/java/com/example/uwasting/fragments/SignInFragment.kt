package com.example.uwasting.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import com.example.uwasting.R
import com.example.uwasting.activities.MainActivity
import com.example.uwasting.activities.StartingActivity
import com.google.android.material.appbar.MaterialToolbar


class SignInFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_sign_in, container, false)
        val startingActivity = activity as StartingActivity
        val toolbar = view.findViewById<MaterialToolbar>(R.id.toolbar)
        val passwordEdit = view.findViewById<EditText>(R.id.pswrd_edit)
        val emailEdit = view.findViewById<EditText>(R.id.email_edit)
        val signInBtn = view.findViewById<Button>(R.id.sign_in_btn)

        signInBtn.setOnClickListener {
            if ((passwordEdit.text.toString() contentEquals "") or (emailEdit.text.toString() contentEquals "")) {
                // TODO("ДОБАВИТЬ ОШИБКУ О ПУСТЫХ ПОЛЯХ")
            }
            /* else if (!(passwordEdit.text.toString() contentEquals startingActivity.user.password) or
                !(emailEdit.text.toString() contentEquals startingActivity.user.email)) {
                // TODO("ДОБАВИТЬ ОШИБКУ О ТОМ, ЧТО ВВЕДЕНЫ НЕКОРРЕКТНЫЕ ДАННЫЕ")
            }
             */ // Здесь должна быть проверка на совпадение данных из бд и данных, что ввёл пользователь
            else {
                startingActivity.user.email = emailEdit.text.toString() // Получаем мыло
                val intent = Intent(startingActivity, MainActivity::class.java)
                startingActivity.startActivity(intent)
            }

            toolbar.setNavigationOnClickListener {
                startingActivity.prevFragment()
            }
        }
        return view
    }

}