package com.example.uwasting.data

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.uwasting.R

class CategoryRecyclerView(private val data:ArrayList<Triple<Category, Int, Int>>):RecyclerView.Adapter<CategoryRecyclerView.MyViewHolder>()  {

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val CategoryNameTextView: TextView = itemView.findViewById(R.id.CategoryNameTextView)
        val TotalTextView: TextView = itemView.findViewById(R.id.TotalTextView)
        val AmountTextView: TextView = itemView.findViewById(R.id.AmountTextView)
        val ImageCategory: ImageView = itemView.findViewById(R.id.ImageCategory)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.categoryviewrecycler_item, parent, false)
        return MyViewHolder(itemView)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.CategoryNameTextView.text = data[position].first.name
        holder.TotalTextView.text = "Всего операций: ${data[position].second}"
        if (data[position].third>0) holder.AmountTextView.text = "+${data[position].third}$"
        else holder.AmountTextView.text = "${data[position].third}$"
        holder.ImageCategory.setImageResource(data[position].first.srcImage)
    }

    override fun getItemCount(): Int {
        return data.size
    }

}