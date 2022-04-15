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
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.example.uwasting.R
import com.example.uwasting.activities.MainActivity
import com.example.uwasting.data.Constants
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.textfield.TextInputEditText
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*


// Фрагмент с добавлением расхода
class NewExpenseFragment : Fragment(), SetCategory {
    @RequiresApi(Build.VERSION_CODES.O)
    val time: LocalDate = LocalDate.now()
    @RequiresApi(Build.VERSION_CODES.O)
    var myYear = time.year
    @RequiresApi(Build.VERSION_CODES.O)
    var myMonth = time.monthValue-1
    @RequiresApi(Build.VERSION_CODES.O)
    var myDay = time.dayOfMonth
    lateinit var datetxt: TextInputEditText
    lateinit var categoryEdit:TextInputEditText
    var compositeDisposable = CompositeDisposable()
    private var category:String =""
    private var isSendingOperation = false
    @RequiresApi(Build.VERSION_CODES.O)
    fun sendOperation(amount:Int, category:String, date:String){
        if (isSendingOperation) return

        val mainActivity = activity as MainActivity
        isSendingOperation = true
        mainActivity.uwastingApi.let {
            compositeDisposable.add(mainActivity.uwastingApi.addOperation(-amount, category, date, mainActivity.user.id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if (it!=-1){
                        mainActivity.totalOperations.addOperation(-amount, category, LocalDate.parse(date,
                            DateTimeFormatter.ofPattern("MM-dd-yyyy", Locale.ENGLISH))  , it)
                        mainActivity.updateCurrentOperations()
                        mainActivity.prevFragment()

                    }
                }, {
                    val text = getString(R.string.add_error)
                    val toast = Toast.makeText(mainActivity, text, Toast.LENGTH_LONG)
                    toast.show()
                    isSendingOperation = false
                }))
        }
    }

    @SuppressLint("SetTextI18n", "SimpleDateFormat")
    var myCallBack =
        OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            myYear = year
            myMonth = monthOfYear
            myDay = dayOfMonth
            val format = SimpleDateFormat("MM-dd-yyyy")
            val calendar: Calendar = Calendar.getInstance()
            calendar.set(myYear, myMonth, myDay)

            datetxt.setText(format.format(calendar.getTime()))
        }

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
        categoryEdit = view.findViewById(R.id.category_edit)
        val addBtn = view.findViewById<Button>(R.id.add_btn)
        val amountTxt = view.findViewById<TextInputEditText>(R.id.sum_edit)
        datetxt = view.findViewById(R.id.cmsn_edit)

        addBtn.setOnClickListener{
            if (amountTxt.text.toString()!="" && categoryEdit.text.toString()!="" && datetxt.text.toString()!=""){
                sendOperation(amountTxt.text.toString().toInt(), categoryEdit.text.toString(), datetxt.text.toString())
            }
            else{
                val text = getString(R.string.field_is_empty)
                val toast = Toast.makeText(mainActivity, text, Toast.LENGTH_LONG)
                toast.show()
            }
        }

        datetxt.setOnClickListener{
            DatePickerDialog(mainActivity, myCallBack,myYear, myMonth,myDay).show()

        }

        // Перемещение на предыдущий фрагмент
        toolbar.setNavigationOnClickListener {
            mainActivity.prevFragment()
        }

        // Выбор категории
        categoryEdit.setOnClickListener() {
            mainActivity.setFragment(SelectCategoryFragment(this, Constants.EXPENSES))
        }
        return view
    }

    override fun onResume() {
        super.onResume()
        categoryEdit.setText(category)
    }

    override fun setCategory(category: String) {
        this.category = category
    }

}