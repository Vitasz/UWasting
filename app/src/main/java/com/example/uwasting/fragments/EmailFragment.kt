package com.example.uwasting.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.uwasting.R
import com.example.uwasting.activities.StartingActivity
import com.example.uwasting.data.remote.UWastingApi
import com.google.android.material.appbar.MaterialToolbar
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers


class EmailFragment : Fragment() {

    private val compositeDisposable = CompositeDisposable()


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
                val text = getString(R.string.field_is_empty)
                val toast = Toast.makeText(startingActivity, text, Toast.LENGTH_LONG)
                toast.show()
            }
            else {
                // Получаем логин
                startingActivity.user.email = emailEdit.text.toString()
                // Проверка доступности логина
                checkMail(startingActivity.uwastingApi, startingActivity.user.email)
            }
        }

        toolbar.setNavigationOnClickListener {
            startingActivity.prevFragment()
        }
        return view
    }

    private fun checkMail(uwastingApi: UWastingApi?, email: String) {
        val startingActivity = activity as StartingActivity

        uwastingApi?.let {
            compositeDisposable.add(uwastingApi.checkEmail(email)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if (it){
                        val text = getString(R.string.email_is_used)
                        val toast = Toast.makeText(startingActivity, text, Toast.LENGTH_LONG)
                        toast.show()
                    }
                    else {
                        startingActivity.setFragment(NameFragment()) // Переход на страницу с вводом имени и фамилии
                    }
                }, {

                }))
        }
    }

}