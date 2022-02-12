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


class PasswordFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_password, container, false)
        val startingActivity = activity as StartingActivity
        val passwordEdit = view.findViewById<EditText>(R.id.enter_pswrd_edit)
        val repPasswordEdit = view.findViewById<EditText>(R.id.repeat_pswrd_edit)
        val toolbar = view.findViewById<MaterialToolbar>(R.id.toolbar)
        val nextBtn = view.findViewById<Button>(R.id.next_btn)

        nextBtn.setOnClickListener {
            if ((passwordEdit.text.toString() contentEquals "") or (repPasswordEdit.text.toString() contentEquals "")) {
                // TODO("ДОБАВИТЬ ОШИБКУ О ПУСТЫХ ПОЛЯХ")
            }
            else if (!(passwordEdit.text.toString() contentEquals repPasswordEdit.text.toString())) {
                // TODO("ДОБАВИТЬ ОШИБКУ О ТОМ, ЧТО ПАРОЛИ НЕ СОВПАДАЮТ")
            }
            else {
                startingActivity.user.password = passwordEdit.text.toString() // Получаем пароль
                startingActivity.setFragment(VerifyEmailFragment()) // Переходим к странице с подтвержден8ием почты
            }
        }

        toolbar.setNavigationOnClickListener {
            startingActivity.prevFragment()
        }

        return view
    }

}