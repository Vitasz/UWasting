package com.example.uwasting.dialogs

import android.content.Context
import android.os.Build
import android.widget.Button
import androidx.annotation.RequiresApi
import com.example.uwasting.R
import com.example.uwasting.activities.MainActivity
import com.example.uwasting.fragments.OnSetBaseOperation
import com.google.android.material.bottomsheet.BottomSheetDialog
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlin.math.abs

// Диалоговое окно удаления операции
@RequiresApi(Build.VERSION_CODES.O)
class OperationDialog(context: Context, private var id: Int, private var amount:Int, private var mainActivity: MainActivity, private var onSetBaseOperation: OnSetBaseOperation): BottomSheetDialog(context) {
    private var compositeDisposable = CompositeDisposable()
    init {
        setContentView(R.layout.dialog_operation)
        val deleteBtn = findViewById<Button>(R.id.delete_btn)
        val makeBase = findViewById<Button>(R.id.make_base_btn)
        deleteBtn?.setOnClickListener{
            mainActivity.uwastingApi.let {
                compositeDisposable.add(mainActivity.uwastingApi.deleteOperation(id)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        if (it) {
                            mainActivity.currentOperations.removeOperation(id)
                            onSetBaseOperation.onSet()
                            this.dismiss()
                        }
                    }, {
                    }))
            }
        }
        makeBase?.setOnClickListener{
            mainActivity.curr="у.е."
            mainActivity.ue = abs(amount)
            onSetBaseOperation.onSet()
            this.dismiss()
        }
    }


}