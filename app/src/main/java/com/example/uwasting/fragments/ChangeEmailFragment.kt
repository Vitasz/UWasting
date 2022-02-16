package com.example.uwasting.fragments

import android.os.Bundle
import android.util.Log
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

// Фрагмент со сменой почты
class ChangeEmailFragment : Fragment() {
    private var compositeDisposable = CompositeDisposable()
    fun ChangeEmail(uwastingApi: UWastingApi, login: String){
        val mainActivity = activity as MainActivity
        uwastingApi?.let {
            compositeDisposable.add(uwastingApi.ChangeLogin(mainActivity.user.id, login)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    mainActivity.user.email = login
                    mainActivity.setFragment(TabFragment())
                }, {
                    val text = getString(R.string.email_is_used)
                    val t = Toast.makeText(mainActivity, text, Toast.LENGTH_LONG)
                    t.show()
                }))
        }

    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_change_email, container, false)
        val mainActivity = activity as MainActivity

        // Получение виджетов
        val toolbar = view.findViewById<MaterialToolbar>(R.id.toolbar)
        val add_btn = view.findViewById<Button>(R.id.add_btn)
        val newEmailTextView = view.findViewById<TextView>(R.id.new_email_edit)
        val nowEmailTextView = view.findViewById<TextView>(R.id.cur_email_edit)

        nowEmailTextView.text = mainActivity.user.email
        add_btn.setOnClickListener(){
            if (newEmailTextView.text.toString()!=""){
                ChangeEmail(mainActivity.uwastingApi, newEmailTextView.text.toString())
            }
            else{
                val text = getString(R.string.field_is_empty)
                val t = Toast.makeText(mainActivity, text, Toast.LENGTH_LONG)
                t.show()
            }
        }
        // Переход на предыдущий фрагмент
        toolbar.setNavigationOnClickListener {
            mainActivity.prevFragment()
        }

        return view
    }


}