package com.example.uwasting.fragments

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.example.uwasting.R
import com.example.uwasting.activities.MainActivity
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.textfield.TextInputEditText
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.time.LocalDate


// Фрагмент с добавлением расхода
class NewExpenseFragment : Fragment() {

    @RequiresApi(Build.VERSION_CODES.O)
    val time = LocalDate.now()
    @RequiresApi(Build.VERSION_CODES.O)
    var curYear = time.year
    @RequiresApi(Build.VERSION_CODES.O)
    var curMonth = time.monthValue
    @RequiresApi(Build.VERSION_CODES.O)
    var curDay = time.dayOfMonth

    lateinit var dateTxt: TextInputEditText
    var compositeDisposable = CompositeDisposable()



    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_new_expense, container, false)
        val mainActivity = activity as MainActivity
        time.year
        // Получение виджетов
        val toolbar = view.findViewById<MaterialToolbar>(R.id.toolbar)
        val categoryEdit = view.findViewById<TextInputEditText>(R.id.category_edit)
        val addBtn = view.findViewById<Button>(R.id.add_btn)
        val amountTxt = view.findViewById<TextInputEditText>(R.id.sum_edit)
        dateTxt = view.findViewById<TextInputEditText>(R.id.cmsn_edit)

        addBtn.setOnClickListener{
            if (amountTxt.text.toString()!="" && categoryEdit.text.toString()!="" && dateTxt.text.toString()!=""){
                sendOperation(amountTxt.text.toString().toInt(), categoryEdit.text.toString(), dateTxt.text.toString())
            }
            else{
                //TODO ПУСТЫЕ ПОЛЯ
            }
        }

        dateTxt.setOnClickListener{
            DatePickerDialog(mainActivity, callBack,curYear, curMonth,curDay).show()
        }

        // Перемещение на предыдущий фрагмент
        toolbar.setNavigationOnClickListener {

            mainActivity.prevFragment()
        }

        // Выбор категории
        categoryEdit.setOnFocusChangeListener { view, focused ->  if (focused) {
            view.clearFocus()
            mainActivity.setFragment(SelectCategoryFragment())
        } }
        return view
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun sendOperation(amount:Int, category:String, date:String){
        val mainActivity = activity as MainActivity

        mainActivity.uwastingApi?.let {
            compositeDisposable.add(mainActivity.uwastingApi.AddOperation(-amount, category, date, mainActivity.user.id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if (it){
                        mainActivity.GetOperations()
                        mainActivity.prevFragment()

                    }
                }, {
                    //TODO ОШИБКА
                }))
        }
    }

    @SuppressLint("SetTextI18n")
    var callBack = OnDateSetListener {view, year, monthOfYear, dayOfMonth ->
            curYear = year
            curMonth = monthOfYear
            curDay = dayOfMonth
            dateTxt.setText("$curMonth-$curDay-$curYear")
    }

}