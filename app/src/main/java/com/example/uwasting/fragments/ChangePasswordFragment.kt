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
import com.example.uwasting.data.remote.UWastingApi
import com.google.android.material.appbar.MaterialToolbar
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

// Фрагмент со сменой пароля
class ChangePasswordFragment : Fragment() {
    private var compositeDisposable = CompositeDisposable()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_change_password, container, false)
        val mainActivity = activity as MainActivity

        // Получение виджетов
        val toolbar = view.findViewById<MaterialToolbar>(R.id.toolbar)
        val addBtn = view.findViewById<Button>(R.id.add_btn)
        val pswrdTextView = view.findViewById<TextView>(R.id.new_pswrd_edit)
        val repPswrdTextView = view.findViewById<TextView>(R.id.repeat_pswrd_edit)

        addBtn.setOnClickListener {
            if (pswrdTextView.text.toString() != "" &&
                repPswrdTextView.text.toString() == pswrdTextView.text.toString()) {
                changePassword(mainActivity.uwastingApi, pswrdTextView.text.toString())
            }
            else if (pswrdTextView.text.toString() != "") {
                val text = getString(R.string.field_is_empty)
                val toast = Toast.makeText(mainActivity, text, Toast.LENGTH_LONG)
                toast.show()
            }
            else {
                val text = getString(R.string.passwords_dont_match)
                val toast = Toast.makeText(mainActivity, text, Toast.LENGTH_LONG)
                toast.show()
            }
        }

        // Переход на предыдущий фрагмент
        toolbar.setNavigationOnClickListener {
            mainActivity.prevFragment()
        }

        return view
    }

    // Изменение пароля
    private fun changePassword(uwastingApi: UWastingApi, password: String){
        val mainActivity = activity as MainActivity
        uwastingApi.let {
            compositeDisposable.add(uwastingApi.changePassword(mainActivity.user.id, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    mainActivity.setFragment(TabFragment())
                }, {
                    val text = getString(R.string.impossible_to_change_password)
                    val toast = Toast.makeText(mainActivity, text, Toast.LENGTH_LONG)
                    toast.show()
                }))
        }

    }

}