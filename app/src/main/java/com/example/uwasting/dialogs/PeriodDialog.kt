package com.example.uwasting.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.annotation.RequiresApi
import androidx.fragment.app.DialogFragment
import com.example.uwasting.R
import com.example.uwasting.activities.MainActivity
import com.example.uwasting.data.Operation
import com.example.uwasting.data.OperationsList

// Диалоговое окно выбора периода
class PeriodDialog: DialogFragment() {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity)
        val view = activity?.layoutInflater?.inflate(R.layout.dialog_period, null)
        builder.setView(view)
        return builder.create()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState:Bundle?) {
        super.onViewCreated(view, savedInstanceState);
        var mainActivity = activity as MainActivity
        var period30btn = view.findViewById<Button>(R.id.month_btn)
        val period90btn = view.findViewById<Button>(R.id.quarter_btn)
        val period180btn = view.findViewById<Button>(R.id.half_year_btn)
        val period365btn = view.findViewById<Button>(R.id.year_btn)

        period30btn.setOnClickListener {
            mainActivity.currentOperations =
                OperationsList(mainActivity.totalOperations.SelectOperations(30))
            Log.d("TAG", "HERE")
        }
        period90btn.setOnClickListener {
            mainActivity.currentOperations =
                OperationsList(mainActivity.totalOperations.SelectOperations(90))
        }
        period180btn.setOnClickListener {
            mainActivity.currentOperations =
                OperationsList(mainActivity.totalOperations.SelectOperations(180))
        }
        period365btn.setOnClickListener {
            mainActivity.currentOperations =
                OperationsList(mainActivity.totalOperations.SelectOperations(365))
        }
    }
}