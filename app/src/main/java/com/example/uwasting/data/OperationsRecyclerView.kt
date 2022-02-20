package com.example.uwasting.data

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.uwasting.R
import com.example.uwasting.activities.MainActivity
import java.time.LocalDate
import kotlin.math.round

// Список с операциями
interface OnOperationClickListener{
    fun onItemClick(item:Triple<LocalDate, Category, Int>)
}
class OperationsRecyclerView(private val data:ArrayList<Triple<LocalDate, Category, Int>>,
                             private var onOperationClickListener: OnOperationClickListener,
                             private var mainActivity:MainActivity):
    RecyclerView.Adapter<OperationsRecyclerView.OperationViewHolder>()  {

    class OperationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var dateTxt: TextView = itemView.findViewById(R.id.date_txt)
        var nameTxt: TextView = itemView.findViewById(R.id.name_txt)
        var sumTxt: TextView = itemView.findViewById(R.id.sum_txt)
        fun bind(item:Triple<LocalDate, Category, Int>, onOperationClickListener: OnOperationClickListener){
            itemView.setOnClickListener {
                onOperationClickListener.onItemClick(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OperationViewHolder {
        val itemView =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.view_operation, parent, false)
        return OperationViewHolder(itemView)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: OperationViewHolder, position: Int) {
        holder.dateTxt.text = data[position].first.toString()
        holder.nameTxt.text = data[position].second.name
        if (data[position].third>0)
            holder.sumTxt.text = "+${round(data[position].third.toFloat()/mainActivity.ue*100)/100.0}" +
                    mainActivity.curr
        else
            holder.sumTxt.text = "${round(data[position].third.toFloat()/mainActivity.ue*100)/100.0}" +
                    mainActivity.curr

        val item = data[position]
        holder.bind(item, onOperationClickListener)
    }

    override fun getItemCount(): Int {
        return data.size
    }

}