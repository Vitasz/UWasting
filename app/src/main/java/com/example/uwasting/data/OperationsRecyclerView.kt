package com.example.uwasting.data

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.uwasting.R
import java.time.LocalDate
import java.util.Date

class OperationsRecyclerView(private val data:ArrayList<Triple<LocalDate, Category, Int>>):RecyclerView.Adapter<OperationsRecyclerView.MyViewHolder>()  {

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var datetxt: TextView = itemView.findViewById(R.id.operation_date)
        var img: ImageView = itemView.findViewById(R.id.operation_img)
        var categorytxt: TextView = itemView.findViewById(R.id.operation_category)
        var amounttxt: TextView = itemView.findViewById(R.id.operation_amount)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.operationviewrecycler_item, parent, false)
        return MyViewHolder(itemView)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.datetxt.text = data[position].first.toString()
        holder.img.setImageResource(data[position].second.srcImage)
        holder.categorytxt.text = data[position].second.name
        if (data[position].third>0)
            holder.amounttxt.text = "+${data[position].third}$"
        else holder.amounttxt.text = "${data[position].third}$"
    }

    override fun getItemCount(): Int {
        return data.size
    }

}