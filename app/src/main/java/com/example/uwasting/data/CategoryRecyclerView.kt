package com.example.uwasting.data

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.uwasting.R

class CategoryRecyclerView(private val data:ArrayList<Triple<Category, Int, Int>>):
    RecyclerView.Adapter<CategoryRecyclerView.CategoryViewHolder>()  {

    class CategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTxt: TextView = itemView.findViewById(R.id.name_txt)
        val operationsAmountTxt: TextView = itemView.findViewById(R.id.operations_txt)
        val sumTxt: TextView = itemView.findViewById(R.id.sum_txt)
        val categoryImg: ImageView = itemView.findViewById(R.id.category_img)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val itemView =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.view_category, parent, false)
        return CategoryViewHolder(itemView)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.nameTxt.text = data[position].first.name
        holder.operationsAmountTxt.text = "Всего операций: ${data[position].second}"
        if (data[position].third>0) holder.sumTxt.text = "+${data[position].third}$"
        else holder.sumTxt.text = "${data[position].third}$"
        holder.categoryImg.setImageResource(data[position].first.srcImage)
    }

    override fun getItemCount(): Int {
        return data.size
    }

}