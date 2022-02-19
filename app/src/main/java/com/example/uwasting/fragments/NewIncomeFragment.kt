package com.example.uwasting.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.uwasting.R
import com.example.uwasting.activities.MainActivity
import com.example.uwasting.data.OperationsList
import com.example.uwasting.data.remote.UWastingApi
import com.example.uwasting.dialogs.PeriodDialog
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.sql.Date

// Фрагмент добавления дохода
class NewIncomeFragment : Fragment() {

    private val compositeDisposable = CompositeDisposable()
    private lateinit var uwastingApi: UWastingApi

    fun AddIncome(amount:Int, category:String, date: Date){
        var mainActivity = activity as MainActivity
        uwastingApi?.let {
            compositeDisposable.add(uwastingApi.AddOperation(amount, category, date, mainActivity.user.id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if (it) {
                        mainActivity.prevFragment()
                    }
                }, {
                    //TODO ВОЗНИКЛА ОШИБКА
                }))
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_new_income, container, false)
        val mainActivity = activity as MainActivity
        uwastingApi = mainActivity.uwastingApi
        // Получение виджетов
        val toolbar = view.findViewById<MaterialToolbar>(R.id.toolbar)
        val categoryEdit = view.findViewById<TextInputEditText>(R.id.category_edit)
        val addBtn = view.findViewById<Button>(R.id.add_btn)
        // перемещение на предыдущий фрагмент
        toolbar.setNavigationOnClickListener {
            mainActivity.prevFragment()
        }
        //Добавление дохода
        addBtn.setOnClickListener{

        }
        // Выбор категории
        categoryEdit.setOnFocusChangeListener { view, focused ->  if (focused) {
            view.clearFocus()
            mainActivity.setFragment(SelectCategoryFragment())
        } }

        return view
    }


}