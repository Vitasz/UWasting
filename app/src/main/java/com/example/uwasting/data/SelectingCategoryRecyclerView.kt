package com.example.uwasting.data

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.uwasting.R

class SelectingCategoryRecyclerView: RecyclerView.Adapter<SelectingCategoryRecyclerView.SelectingCategoryViewHolder>() {

    private val categoriesList: ArrayList<Category> = ArrayList()
    private var selectedCategory: SelectingCategoryViewHolder? = null

    class SelectingCategoryViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var categoryImg: ImageView = itemView.findViewById(R.id.category_img)
        var nameTxt: TextView = itemView.findViewById(R.id.name_txt)
        var selectBtn: RadioButton = itemView.findViewById(R.id.selectBtn)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SelectingCategoryViewHolder {
        val itemView =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.view_selecting_category, parent, false)
        return SelectingCategoryViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: SelectingCategoryViewHolder, position: Int) {
        holder.nameTxt.text = categoriesList[position].name
        holder.categoryImg.setImageResource(categoriesList[position].srcImage)
        holder.selectBtn.setOnClickListener {
            if (selectedCategory != null)
                selectedCategory?.selectBtn?.isChecked = false
            selectedCategory = holder
        }
    }

    override fun getItemCount(): Int {
        return categoriesList.size
    }

    fun addItem(category: Category) {
        categoriesList.add(category)
        notifyDataSetChanged()
    }
}