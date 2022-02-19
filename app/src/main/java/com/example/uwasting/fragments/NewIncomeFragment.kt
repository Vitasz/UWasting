package com.example.uwasting.fragments

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.uwasting.R
import com.example.uwasting.activities.MainActivity
import com.example.uwasting.data.Operation
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
import java.time.LocalDate

// Фрагмент добавления дохода
class NewIncomeFragment : Fragment() {
    @RequiresApi(Build.VERSION_CODES.O)
    val time: LocalDate = LocalDate.now()
    @RequiresApi(Build.VERSION_CODES.O)
    var myYear = time.year
    @RequiresApi(Build.VERSION_CODES.O)
    var myMonth = time.monthValue
    @RequiresApi(Build.VERSION_CODES.O)
    var myDay = time.dayOfMonth
    lateinit var datetxt: TextInputEditText
    var compositeDisposable = CompositeDisposable()

    @SuppressLint("SetTextI18n")
    var myCallBack =
        DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            myYear = year
            myMonth = monthOfYear
            myDay = dayOfMonth
            datetxt.setText("$myMonth-$myDay-$myYear")
        }

    private lateinit var uwastingApi: UWastingApi

    @RequiresApi(Build.VERSION_CODES.O)
    fun AddIncome(amount:Int, category:String, date: String){
        var mainActivity = activity as MainActivity
        uwastingApi?.let {
            compositeDisposable.add(uwastingApi.AddOperation(amount, category, date, mainActivity.user.id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if (it) {
                        mainActivity.GetOperations()
                        mainActivity.prevFragment()
                    }
                }, {
                    //TODO ВОЗНИКЛА ОШИБКА
                }))
        }
    }
    @RequiresApi(Build.VERSION_CODES.O)
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
        val amountxt = view.findViewById<TextInputEditText>(R.id.sum_edit)
        datetxt = view.findViewById(R.id.cmsn_edit)


        addBtn.setOnClickListener{

            if (amountxt.text.toString()!="" && categoryEdit.text.toString()!="" && datetxt.text.toString()!=""){
                AddIncome(amountxt.text.toString().toInt(), categoryEdit.text.toString(), datetxt.text.toString())
            }
            else{
                //TODO ПУСТЫЕ ПОЛЯ
            }
        }


        datetxt.setOnClickListener{
            Log.d("HERE", "HERE")
            DatePickerDialog(mainActivity, myCallBack,myYear, myMonth,myDay).show()

        }

        // Выбор категории
        categoryEdit.setOnFocusChangeListener { view, focused ->  if (focused) {
            view.clearFocus()
            mainActivity.setFragment(SelectCategoryFragment())
        } }
        // перемещение на предыдущий фрагмент
        toolbar.setNavigationOnClickListener {
            mainActivity.prevFragment()
        }
        return view
    }


}