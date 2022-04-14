package com.example.uwasting.fragments

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.example.uwasting.R
import com.example.uwasting.activities.MainActivity
import com.example.uwasting.data.Constants
import com.example.uwasting.data.remote.UWastingApi
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.textfield.TextInputEditText
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*

interface SetCategory{
    fun setCategory(category:String)
}
// Фрагмент добавления дохода
class NewIncomeFragment : Fragment(), SetCategory {
    @RequiresApi(Build.VERSION_CODES.O)
    val time: LocalDate = LocalDate.now()
    @RequiresApi(Build.VERSION_CODES.O)
    var curYear = time.year
    @RequiresApi(Build.VERSION_CODES.O)
    var curMonth = time.monthValue-1
    @RequiresApi(Build.VERSION_CODES.O)
    var curDay = time.dayOfMonth

    lateinit var dateTxt: TextInputEditText
    var compositeDisposable = CompositeDisposable()
    private lateinit var uwastingApi: UWastingApi
    lateinit var categoryText: TextInputEditText
    private var category:String =""
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
        categoryText = view.findViewById(R.id.category_edit)
        val addBtn = view.findViewById<Button>(R.id.add_btn)
        val amountTxt = view.findViewById<TextInputEditText>(R.id.sum_edit)
        dateTxt = view.findViewById(R.id.cmsn_edit)


        addBtn.setOnClickListener{
            if (amountTxt.text.toString()!="" && categoryText.text.toString()!="" && dateTxt.text.toString()!=""){
                addIncome(amountTxt.text.toString().toInt(), categoryText.text.toString(), dateTxt.text.toString())
            }
            else{
                val text = getString(R.string.field_is_empty)
                val toast = Toast.makeText(mainActivity, text, Toast.LENGTH_LONG)
                toast.show()
            }
        }

        dateTxt.setOnClickListener{
            DatePickerDialog(mainActivity, callBack,curYear, curMonth,curDay).show()
        }

        // Выбор категории
        categoryText.setOnClickListener {
                mainActivity.setFragment(SelectCategoryFragment(this, Constants.INCOMES))
        }

        // перемещение на предыдущий фрагмент
        toolbar.setNavigationOnClickListener {
            mainActivity.prevFragment()
        }
        return view
    }

    @SuppressLint("SetTextI18n", "SimpleDateFormat")
    var callBack = DatePickerDialog.OnDateSetListener {view, year, monthOfYear, dayOfMonth ->
        curYear = year
        curMonth = monthOfYear+1
        curDay = dayOfMonth
        val format = SimpleDateFormat("MM-dd-yyyy")
        val calendar: Calendar = Calendar.getInstance()
        calendar.set(curYear, curMonth, curDay)
        dateTxt.setText(format.format(calendar.getTime()))
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun addIncome(amount:Int, category:String, date: String){
        val mainActivity = activity as MainActivity
        uwastingApi.let {
            compositeDisposable.add(uwastingApi.addOperation(amount, category, date, mainActivity.user.id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if (it!=-1){
                        val year = date.split('-')[2]
                        val month = date.split('-')[0]
                        val day = date.split('-')[1]

                        mainActivity.currentOperations.addOperation(amount, category, year+'-'+month+'-'+day, it)
                        mainActivity.prevFragment()
                    }
                }, {
                    val text = getString(R.string.add_error)
                    val toast = Toast.makeText(mainActivity, text, Toast.LENGTH_LONG)
                    toast.show()
                }))
        }
    }

    override fun onResume() {
        super.onResume()
        categoryText.setText(category)
    }
    override fun setCategory(category:String) {
        this.category = category
    }

}