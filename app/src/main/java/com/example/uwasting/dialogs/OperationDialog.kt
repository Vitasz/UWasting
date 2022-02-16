package com.example.uwasting.dialogs

import android.content.Context
import com.example.uwasting.R
import com.google.android.material.bottomsheet.BottomSheetDialog

// Диалоговое окно редактирования операции
class OperationDialog(context: Context): BottomSheetDialog(context) {

    init {
        setContentView(R.layout.dialog_operation)

    }

}