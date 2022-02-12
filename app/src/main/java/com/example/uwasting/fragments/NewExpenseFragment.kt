package com.example.uwasting.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.uwasting.R
import com.example.uwasting.activities.MainActivity
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.textfield.TextInputEditText

// Фрагмент с добавлением расхода
class NewExpenseFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_new_expense, container, false)
        val mainActivity = activity as MainActivity

        // Получение виджетов
        val toolbar = view.findViewById<MaterialToolbar>(R.id.toolbar)
        val categoryEdit = view.findViewById<TextInputEditText>(R.id.category_edit)

        // Перемещение на предыдущий фрагмент
        toolbar.setNavigationOnClickListener {
            mainActivity.prevFragment()
        }

        // Выбор категории
        categoryEdit.setOnFocusChangeListener { view, focused ->  if (focused) {
            view.clearFocus()
            mainActivity.setFragment(SelectCategoryFragment())
        } }
        return view
    }

}