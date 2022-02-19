package com.example.uwasting.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.annotation.RequiresApi
import androidx.fragment.app.DialogFragment
import com.example.uwasting.R
import com.example.uwasting.activities.MainActivity
import com.example.uwasting.fragments.UpdateFragment

// Диалоговое окно выбора периода
class PeriodDialog(private var mainActivity: MainActivity, private var updateFragment:UpdateFragment): DialogFragment() {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity)
        val view = activity?.layoutInflater?.inflate(R.layout.dialog_period, null)
        var monthBtn = view?.findViewById<Button>(R.id.month_btn)
        var quarterBtn = view?.findViewById<Button>(R.id.quarter_btn)
        var halfYearBtn = view?.findViewById<Button>(R.id.half_year_btn)
        var yearBtn = view?.findViewById<Button>(R.id.year_btn)

        monthBtn?.setOnClickListener{
            mainActivity.Period = 30
            mainActivity.UpdateCurrentOperations()
            updateFragment.update()
            this.dismiss()
        }
        quarterBtn?.setOnClickListener{
            mainActivity.Period = 90
            mainActivity.UpdateCurrentOperations()
            updateFragment.update()
            this.dismiss()
        }
        halfYearBtn?.setOnClickListener{
            mainActivity.Period = 180
            mainActivity.UpdateCurrentOperations()
            updateFragment.update()
            this.dismiss()
        }
        yearBtn?.setOnClickListener{
            mainActivity.Period = 365
            mainActivity.UpdateCurrentOperations()
            updateFragment.update()
            this.dismiss()
        }
        builder.setView(view)
        return builder.create()
    }

}