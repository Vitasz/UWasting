package com.example.uwasting.dialogs

import android.content.Context
import android.os.Build
import android.widget.Button
import androidx.annotation.RequiresApi
import com.example.uwasting.R
import com.example.uwasting.activities.MainActivity
import com.example.uwasting.data.remote.UWastingApi
import com.google.android.material.bottomsheet.BottomSheetDialog
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

// Диалоговое окно удаления операции
@RequiresApi(Build.VERSION_CODES.O)
class OperationDialog(context: Context, private var id: Int, private var mainActivity: MainActivity): BottomSheetDialog(context) {
    private var compositeDisposable = CompositeDisposable()
    init {
        setContentView(R.layout.dialog_operation)
        val delete_btn = findViewById<Button>(R.id.delete_btn)
        delete_btn?.setOnClickListener{
            mainActivity.uwastingApi?.let {
                compositeDisposable.add(mainActivity.uwastingApi.DeleteOperation(id)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        mainActivity.GetOperations()
                        this.dismiss()
                    }, {
                    }))
            }
        }
    }

}