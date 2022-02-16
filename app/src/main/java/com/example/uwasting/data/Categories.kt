package com.example.uwasting.data

import com.example.uwasting.R

class Categories {
    var common = listOf(
    Category(R.drawable.clothes, "Одежда"),
    Category(R.drawable.jewerly, "Ювелирия"),
    Category(R.drawable.products, "Продукты"),
    Category(R.drawable.hobbies, "Хобби"),
    Category(R.drawable.restaurants, "Рестораны"),
    Category(R.drawable.transport, "Транспорт"),
    Category(R.drawable.trip, "Путешествия"))

    fun hasInCommon(name:String):Category{
        for (i in common) {
            if (i.name==name){
                return i
            }
        }
        return Category(R.drawable.other, "Прочее")
    }
}

class Category(srcImage:Int, name:String){
    val srcImage = srcImage
    val name = name
}