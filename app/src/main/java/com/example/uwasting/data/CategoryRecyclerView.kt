package com.example.uwasting.data

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.uwasting.R
import com.example.uwasting.activities.MainActivity
import kotlin.math.round

interface OnItemClickListener{
    fun onItemClicked(item: Triple<Category, Int, Int>)
}
class CategoryRecyclerView(private val data:ArrayList<Triple<Category, Int, Int>>, private val
itemClickListener:OnItemClickListener, private var mainActivity:MainActivity):
    RecyclerView.Adapter<CategoryRecyclerView.CategoryViewHolder>()  {

    class CategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTxt: TextView = itemView.findViewById(R.id.name_txt)
        val operationsAmountTxt: TextView = itemView.findViewById(R.id.operations_txt)
        val sumTxt: TextView = itemView.findViewById(R.id.sum_txt)
        val categoryImg: ImageView = itemView.findViewById(R.id.category_img)
        fun bind(item: Triple<Category, Int, Int>,clickListener: OnItemClickListener)
        {
            itemView.setOnClickListener {
                clickListener.onItemClicked(item)
            }
        }
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
        if (data[position].third>0)
            holder.sumTxt.text =
                "+${round(data[position].third.toFloat()/mainActivity.ue*100)/100.0}"+mainActivity.curr
        else
            holder.sumTxt.text =
                "${round(data[position].third.toFloat()/mainActivity.ue*100)/100.0}"+mainActivity.curr
        holder.categoryImg.setImageResource(data[position].first.srcImage)

        val item = data[position]
        holder.bind(item, itemClickListener)
    }

    override fun getItemCount(): Int {
        return data.size
    }

}