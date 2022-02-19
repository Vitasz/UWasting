package com.example.uwasting.data

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.uwasting.R
import java.time.LocalDate

class OperationsRecyclerView(private val data:ArrayList<Triple<LocalDate, Category, Int>>):
    RecyclerView.Adapter<OperationsRecyclerView.OperationViewHolder>()  {

    class OperationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var dateTxt: TextView = itemView.findViewById(R.id.date_txt)
        var nameTxt: TextView = itemView.findViewById(R.id.name_txt)
        var sumTxt: TextView = itemView.findViewById(R.id.sum_txt)
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
            holder.sumTxt.text = "+${data[position].third}$"
        else holder.sumTxt.text = "${data[position].third}$"
    }

    override fun getItemCount(): Int {
        return data.size
    }

}