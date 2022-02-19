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
interface OnOperationClickListener{
    fun onItemClick(item:Triple<LocalDate, Category, Int>)
}
class OperationsRecyclerView(private val data:ArrayList<Triple<LocalDate, Category, Int>>, private var onOperationClickListener: OnOperationClickListener):RecyclerView.Adapter<OperationsRecyclerView.MyViewHolder>()  {

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var datetxt: TextView = itemView.findViewById(R.id.operation_date)
        var img: ImageView = itemView.findViewById(R.id.operation_img)
        var categorytxt: TextView = itemView.findViewById(R.id.operation_category)
        var amounttxt: TextView = itemView.findViewById(R.id.operation_amount)
        fun bind(item:Triple<LocalDate, Category, Int>, onOperationClickListener: OnOperationClickListener){
            itemView.setOnClickListener {
                onOperationClickListener.onItemClick(item)
            }
        }
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
        var item = data[position]
        holder.bind(item, onOperationClickListener)
    }

    override fun getItemCount(): Int {
        return data.size
    }

}