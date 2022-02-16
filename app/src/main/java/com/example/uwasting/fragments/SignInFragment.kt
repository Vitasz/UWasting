package com.example.uwasting.fragments

import android.content.Intent
import android.content.SearchRecentSuggestionsProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.uwasting.R
import com.example.uwasting.activities.MainActivity
import com.example.uwasting.activities.StartingActivity
import com.example.uwasting.data.remote.UWastingApi
import com.google.android.material.appbar.MaterialToolbar
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers


class SignInFragment : Fragment() {

    private val compositeDisposable = CompositeDisposable()

    private fun tryGet(uwastingApi: UWastingApi?, email: String, password: String) {
        val startingActivity = activity as StartingActivity
        uwastingApi?.let {
            compositeDisposable.add(uwastingApi.getUserData(email, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    Log.e("USER: ", it.toString())
                    val intent = Intent(startingActivity, MainActivity::class.java)
                    intent.putExtra("UserName", it.name)
                    intent.putExtra("UserSurName", it.surname)
                    intent.putExtra("UserId", it.id)
                    intent.putExtra("UserEmail", it.email)
                    startingActivity.startActivity(intent)
                }, {
                    val text = getString(R.string.user_not_found)
                    val t = Toast.makeText(startingActivity, text, Toast.LENGTH_LONG)
                    t.show()
                }))
        }
    }

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
                val text = getString(R.string.field_is_empty)
                val t = Toast.makeText(startingActivity, text, Toast.LENGTH_LONG)
                t.show()
            }

            else {
                startingActivity.user.password = passwordEdit.text.toString() // Получаем пароль
                startingActivity.user.email = emailEdit.text.toString() // Получаем мыло
                tryGet(startingActivity.uwastingApi, startingActivity.user.email, startingActivity.user.password)

            }
        }

        toolbar.setNavigationOnClickListener {
            startingActivity.prevFragment()
        }

        return view
    }

}