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
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.example.uwasting.R
import com.example.uwasting.activities.MainActivity
import com.example.uwasting.activities.StartingActivity
import com.example.uwasting.data.User
import com.example.uwasting.data.remote.UWastingApi
import com.google.android.material.appbar.MaterialToolbar
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers


class PasswordFragment : Fragment() {

    private val compositeDisposable = CompositeDisposable()
    private fun TryRegistrateUser(uwastingApi: UWastingApi, user: User){
        val startingActivity = activity as StartingActivity
        uwastingApi?.let {
            compositeDisposable.add(uwastingApi.RegistrateUser(user.email,user.password, user.name, user.surname)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    Log.e("USER: ", it.toString())
                    val intent = Intent(startingActivity, MainActivity::class.java)
                    intent.putExtra("UserName", user.name)
                    intent.putExtra("UserSurName", user.surname)
                    intent.putExtra("UserId", user.id)
                    intent.putExtra("UserEmail", user.email)
                    startingActivity.startActivity(intent)
                }, {
                    val text = getString(R.string.registration_error)
                    val t = Toast.makeText(startingActivity, text, Toast.LENGTH_LONG)
                    t.show()
                }))
        }

    }
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
                val text = getString(R.string.field_is_empty)
                val t = Toast.makeText(startingActivity, text, Toast.LENGTH_LONG)
                t.show()
            }
            else if (!(passwordEdit.text.toString() contentEquals repPasswordEdit.text.toString())) {
                val text = getString(R.string.passwords_dont_match)
                val t = Toast.makeText(startingActivity, text, Toast.LENGTH_LONG)
                t.show()
            }
            else {
                startingActivity.user.password = passwordEdit.text.toString() // Получаем пароль
                TryRegistrateUser(startingActivity.uwastingApi, startingActivity.user)
            }
        }

        toolbar.setNavigationOnClickListener {
            startingActivity.prevFragment()
        }

        return view
    }

}