package com.example.uwasting.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.uwasting.R
import com.example.uwasting.activities.MainActivity
import com.example.uwasting.data.Constants
import com.google.android.material.appbar.MaterialToolbar

// Фрагмент проверки пароля
class VerifyPasswordFragment(mode: Int) : Fragment() {
    private var mode: Int = mode

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_verify_password, container, false)
        val mainActivity = activity as MainActivity

        // Получение виджетов
        val toolbar = view.findViewById<MaterialToolbar>(R.id.toolbar)
        val nextBtn = view.findViewById<Button>(R.id.next_btn)

        // Переход на следующий фрагмент в зависимости от намерения
        nextBtn.setOnClickListener {
            when (mode) {
                Constants.CHANGE_EMAIl -> mainActivity.setFragment(ChangeEmailFragment())
                Constants.CHANGE_PASSWORD -> mainActivity.setFragment(ChangePasswordFragment())
            }
        }

        // Переход на предыдущий фрагмент
        toolbar.setNavigationOnClickListener {
            mainActivity.prevFragment()
        }

        return view
    }


}