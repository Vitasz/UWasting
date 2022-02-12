package com.example.uwasting.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.example.uwasting.R
import com.example.uwasting.activities.MainActivity
import com.example.uwasting.activities.StartingActivity
import com.example.uwasting.data.remote.UWastingApi
import com.google.android.material.appbar.MaterialToolbar
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

// Фрагмент аккаунта
class AccountFragment : Fragment() {

    private val compositeDisposable = CompositeDisposable()
    fun tryChange(uwastingApi: UWastingApi?, id:Int, name:String, surname:String) {
        uwastingApi?.let {
            compositeDisposable.add(uwastingApi.ChangeNameSurname(id, name, surname)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    val mainActivity = activity as MainActivity
                    mainActivity.user.name = name
                    mainActivity.user.surname=surname
                    mainActivity.prevFragment()
                }, {
                    //TODO("ДОБАВИТЬ ОШИБКУ: НЕ УДАЛОСЬ ИЗМЕНИТЬ ИМЯ")
                    Log.d("tag", "пользователь не найден")
                }))
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_account, container, false)
        val mainActivity = activity as MainActivity
        val SaveChangesBtn = view.findViewById<Button>(R.id.add_btn)
        val nameTextView = view.findViewById<TextView>(R.id.name_edit)
        val surnameTextView = view.findViewById<TextView>(R.id.surname_edit)
        //Нажатие на кнопку сохранить изменения
        SaveChangesBtn.setOnClickListener(){
            if (nameTextView.text.toString()=="" || surnameTextView.text.toString()==""){

                //TODO ДОБАВИТЬ ОШИБКУ О ПУСТЫХ ПОЛЯХ
            }
            else{
                tryChange(mainActivity.uwastingApi,mainActivity.user.id, nameTextView.text.toString(), surnameTextView.text.toString())
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