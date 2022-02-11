package com.example.uwasting.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.DialogFragment
import com.example.uwasting.R

class PeriodDialog: DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity)
        val view = activity?.layoutInflater?.inflate(R.layout.dialog_period, null)
        builder.setView(view)
        Log.d("fuck", "HSE")
        return builder.create()
    }
}