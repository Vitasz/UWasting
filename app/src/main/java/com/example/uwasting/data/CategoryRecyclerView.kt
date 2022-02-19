package com.example.uwasting.data

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.uwasting.R
import com.example.uwasting.activities.MainActivity
import kotlin.math.round

interface OnItemClickListener{
    fun onItemClicked(item: Triple<Category, Int, Int>)
}
class CategoryRecyclerView(private val data:ArrayList<Triple<Category, Int, Int>>, private val itemClickListener:OnItemClickListener, private var mainActivity:MainActivity):RecyclerView.Adapter<CategoryRecyclerView.MyViewHolder>()  {

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val CategoryNameTextView: TextView = itemView.findViewById(R.id.CategoryNameTextView)
        val TotalTextView: TextView = itemView.findViewById(R.id.TotalTextView)
        val AmountTextView: TextView = itemView.findViewById(R.id.AmountTextView)
        val ImageCategory: ImageView = itemView.findViewById(R.id.ImageCategory)
        fun bind(item: Triple<Category, Int, Int>,clickListener: OnItemClickListener)
        {
            itemView.setOnClickListener {
                clickListener.onItemClicked(item)
            }
        }
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
        if (data[position].third>0) holder.AmountTextView.text = "+${round(data[position].third.toFloat()/mainActivity.ue*100)/100.0}"+mainActivity.curr
        else holder.AmountTextView.text = "${round(data[position].third.toFloat()/mainActivity.ue*100)/100.0}"+mainActivity.curr
        holder.ImageCategory.setImageResource(data[position].first.srcImage)
        val item = data[position]
        holder.bind(item, itemClickListener)
    }

    override fun getItemCount(): Int {
        return data.size
    }

}