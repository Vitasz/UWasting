package com.example.uwasting.data

import android.graphics.Color
import com.example.uwasting.R
import com.github.mikephil.charting.utils.ColorTemplate

class Categories {
    var common = listOf(
    Category(R.drawable.clothes, "Одежда", Color.rgb(93, 138, 197)),
    Category(R.drawable.jewerly, "Ювелирия", Color.rgb(3, 168,99)),
    Category(R.drawable.products, "Продукты", Color.rgb(246, 144, 121)),
    Category(R.drawable.hobbies, "Хобби", Color.rgb(91, 49, 151)),
    Category(R.drawable.restaurants, "Рестораны", Color.rgb(239, 29, 38)),
    Category(R.drawable.transport, "Транспорт", Color.rgb(89, 199, 200)),
    Category(R.drawable.trip, "Путешествия", Color.rgb(247, 126, 115)))

    val other = Category(R.drawable.other, "Прочее", Color.rgb(192, 129, 184))

    fun hasInCommon(name:String):Category{
        for (i in common) {
            if (i.name==name){
                return i
            }
        }
        return other
    }
}

class Category(val srcImage: Int, val name: String, val color: Int){

}