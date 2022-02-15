package com.example.uwasting.fragments

import android.Manifest
import android.annotation.SuppressLint
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
import com.example.uwasting.dialogs.PeriodDialog
import com.google.android.material.button.MaterialButton
import org.apache.commons.csv.CSVFormat
import org.apache.commons.csv.CSVPrinter
import java.io.File
import java.nio.file.Files
import java.nio.file.Paths



// Фрагмент с доходами
class IncomesFragment : Fragment() {

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("SetTextI18n", "SdCardPath")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_incomes, container, false)
        val mainActivity = activity as MainActivity

        val exportToCSVBtn = view.findViewById<Button>(R.id.export_btn)
        val periodLayout = view.findViewById<ConstraintLayout>(R.id.period_layout)
        val dateTxt = view.findViewById<TextView>(R.id.date_txt)
        val listLayout = view.findViewById<ConstraintLayout>(R.id.list_layout)
        val addIncomeBtn = view.findViewById<MaterialButton>(R.id.add_income_btn)
        val totalIncomesTxt = view.findViewById<TextView>(R.id.sum_txt)
        totalIncomesTxt.text = '+' + mainActivity.operations.GetTotalSumIncomes().toString()

        listLayout.setOnClickListener {
            mainActivity.setFragment(CategoryFragment())
        }

        // Нажатие на период
        periodLayout.setOnClickListener {
            val dialog = PeriodDialog()
            dialog.show(parentFragmentManager, "period")
        }

        addIncomeBtn.setOnClickListener {
            mainActivity.setFragment(NewIncomeFragment())
        }

        exportToCSVBtn.setOnClickListener {
            mainActivity.checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, 123)

            val filename = "incomes.csv"
            var path = context?.getExternalFilesDir(null)
            var fileOut = File(path, filename)
            fileOut.delete()
            fileOut.createNewFile()
            val stringPath = path.toString()

            val operations = mainActivity.operations
            val writer = Files.newBufferedWriter(Paths.get("$stringPath/$filename"))
            val csvPrinter = CSVPrinter(writer, CSVFormat.DEFAULT
                .withHeader("OperationId", "Category", "Amount", "Date"))

            for (operation in operations.list) {
                val operationData = listOf(
                    operation.id,
                    operation.category,
                    operation.amount,
                    operation.date)

                csvPrinter.printRecord(operationData)
            }

            csvPrinter.flush()
            csvPrinter.close()

        }


        return view
    }

}