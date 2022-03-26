package com.example.uwasting.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.RadioButton
import androidx.fragment.app.DialogFragment
import com.example.uwasting.R
import com.example.uwasting.activities.BaseActivity
import com.example.uwasting.activities.MainActivity
import java.util.*

class LanguageDialog: DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity)
        val mainActivity = activity as MainActivity
        val view = activity?.layoutInflater?.inflate(R.layout.dialog_language, null)
        val btnRus = view?.findViewById<RadioButton>(R.id.radioBtnRus)
        val btnEng = view?.findViewById<RadioButton>(R.id.radioBtnEng)

        btnRus?.setOnClickListener {
            mainActivity.language = "strings-ru"
            this.dismiss()
        }

        btnEng?.setOnClickListener {
            mainActivity.language = "strings-en"
            this.dismiss()
        }

        builder.setView(view)
        return builder.create()
    }
}
