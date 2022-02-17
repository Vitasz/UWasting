package com.example.uwasting.fragments

import android.Manifest
import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.uwasting.R
import com.example.uwasting.activities.MainActivity
import org.apache.commons.csv.CSVFormat
import org.apache.commons.csv.CSVPrinter
import java.io.File
import java.nio.file.Files
import java.nio.file.Paths

// Фрагмент с расходами
class ExpensesFragment : Fragment() {

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_expenses, container, false)
        val mainActivity = activity as MainActivity

        // Получение виджетов
        val exportToCSVBtn = view.findViewById<Button>(R.id.export_btn)
        val categoriesList = view.findViewById<ConstraintLayout>(R.id.list_layout)
        val addExpenseBtn = view.findViewById<Button>(R.id.add_expense_btn)
        val totalExpensesTxt = view.findViewById<TextView>(R.id.totalExpenses)
        totalExpensesTxt.text = mainActivity.currentOperations.GetTotalSumExpenses().toString()

        // переход на фрагмент с категориями
        categoriesList.setOnClickListener {
            mainActivity.setFragment(CategoryFragment())
        }

        // Добавление расхода
        addExpenseBtn.setOnClickListener {
            mainActivity.setFragment(NewExpenseFragment())
        }

        exportToCSVBtn.setOnClickListener {
            mainActivity.checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, 123)

            val filename = "expenses.csv"
            var path = context?.getExternalFilesDir(null)
            var fileOut = File(path, filename)
            fileOut.delete()
            fileOut.createNewFile()
            val stringPath = path.toString()

            val operations = mainActivity.currentOperations
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