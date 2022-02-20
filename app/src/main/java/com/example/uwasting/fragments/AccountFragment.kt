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

// Фрагмент аккаунта
class AccountFragment : Fragment() {

    private val compositeDisposable = CompositeDisposable()
    // Изменения имени и фамилии
    private fun tryChange(uwastingApi: UWastingApi?, id:Int, name:String, surname:String) {
        val mainActivity = activity as MainActivity
        uwastingApi?.let {
            compositeDisposable.add(uwastingApi.changeNameSurname(id, name, surname)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    mainActivity.user.name = name
                    mainActivity.user.surname = surname
                    mainActivity.prevFragment()
                }, {
                    val text = getString(R.string.name_error)
                    val toast = Toast.makeText(mainActivity, text, Toast.LENGTH_LONG)
                    toast.show()
                }))
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_account, container, false)
        val mainActivity = activity as MainActivity
        val saveChangesBtn = view.findViewById<Button>(R.id.add_btn)
        val nameTextView = view.findViewById<TextView>(R.id.name_edit)
        val surnameTextView = view.findViewById<TextView>(R.id.surname_edit)
        //Нажатие на кнопку сохранить изменения
        saveChangesBtn.setOnClickListener(){
            if (nameTextView.text.toString()=="" || surnameTextView.text.toString()==""){

                val text = getString(R.string.field_is_empty)
                val toast = Toast.makeText(mainActivity, text, Toast.LENGTH_LONG)
                toast.show()
            }
            else{
                tryChange(mainActivity.uwastingApi,mainActivity.user.id, nameTextView.text.toString(),
                    surnameTextView.text.toString())
            }
        }


        // Получение виджетов
        val toolbar = view.findViewById<MaterialToolbar>(R.id.toolbar)

        // Переход на предыдущий фрагмент
        toolbar.setNavigationOnClickListener {
            mainActivity.prevFragment()
        }

        return view
    }

}