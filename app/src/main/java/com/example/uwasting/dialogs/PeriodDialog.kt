package com.example.uwasting.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.fragment.app.DialogFragment
import com.example.uwasting.R

// Диалоговое окно выбора периода
class PeriodDialog: DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity)
        val view = activity?.layoutInflater?.inflate(R.layout.dialog_period, null)

        val monthBtn = view?.findViewById<Button>(R.id.month_btn)
        val quarterBtn = view?.findViewById<Button>(R.id.quarter_btn)
        val halfYearBtn = view?.findViewById<Button>(R.id.half_year_btn)
        val yearBtn = view?.findViewById<Button>(R.id.year_btn)

        // Выбор периода
        monthBtn?.setOnClickListener {

        }

        quarterBtn?.setOnClickListener {

        }

        halfYearBtn?.setOnClickListener {

        }

        yearBtn?.setOnClickListener {

        }

        builder.setView(view)
        return builder.create()
    }
}