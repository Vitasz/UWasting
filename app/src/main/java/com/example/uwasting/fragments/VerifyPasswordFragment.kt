package com.example.uwasting.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.uwasting.R
import com.example.uwasting.activities.MainActivity
import com.example.uwasting.data.Constants
import com.example.uwasting.data.remote.UWastingApi
import com.google.android.material.appbar.MaterialToolbar
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

// Фрагмент проверки пароля
class VerifyPasswordFragment(mode: Int) : Fragment() {

    private var mode: Int = mode
    private val compositeDisposable = CompositeDisposable()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_verify_password, container, false)
        val mainActivity = activity as MainActivity

        // Получение виджетов
        val toolbar = view.findViewById<MaterialToolbar>(R.id.toolbar)
        val nextBtn = view.findViewById<Button>(R.id.next_btn)
        val passwordTextView = view.findViewById<TextView>(R.id.pswrd_edit)
        // Переход на следующий фрагмент в зависимости от намерения
        nextBtn.setOnClickListener {
            tryLogin(mainActivity.uwastingApi, mainActivity.user.email, passwordTextView.text.toString())
        }

        // Переход на предыдущий фрагмент
        toolbar.setNavigationOnClickListener {
            mainActivity.prevFragment()
        }

        return view
    }

    private fun tryLogin(uwastingApi: UWastingApi, login:String, password:String){
        val mainActivity = activity as MainActivity
        uwastingApi?.let {
            compositeDisposable.add(uwastingApi.getUserData(login, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    when (mode) {
                        Constants.CHANGE_EMAIl -> mainActivity.setFragment(ChangeEmailFragment())
                        Constants.CHANGE_PASSWORD -> mainActivity.setFragment(ChangePasswordFragment())
                    }
                }, {
                    val text = getString(R.string.passwords_dont_match)
                    val t = Toast.makeText(mainActivity, text, Toast.LENGTH_LONG)
                    t.show()
                }))
        }
    }


}