package com.example.uwasting.data

import android.graphics.Color
import com.example.uwasting.R
import com.example.uwasting.activities.MainActivity

class Categories {
    val expenses = listOf(
    Category(R.drawable.clothes, "Одежда", Color.rgb(93, 138, 197)),
    Category(R.drawable.jewerly, "Ювелирия", Color.rgb(3, 168,99)),
    Category(R.drawable.products, "Продукты", Color.rgb(246, 144, 121)),
    Category(R.drawable.hobbies, "Хобби", Color.rgb(91, 49, 151)),
    Category(R.drawable.restaurants, "Рестораны", Color.rgb(239, 29, 38)),
    Category(R.drawable.transport, "Транспорт", Color.rgb(89, 199, 200)),
    Category(R.drawable.trip, "Путешествия", Color.rgb(247, 126, 115)),
    Category(R.drawable.other, "Прочие расходы", Color.rgb(192, 129, 184)))

    val incomes = listOf(
        Category(R.drawable.salary, "Зарплата", Color.rgb(246, 144, 121)),
        Category(R.drawable.pension, "Пенсия", Color.rgb(93, 138, 197)),
        Category(R.drawable.scholarship, "Стипендия", Color.rgb(237, 65, 61)),
        Category(R.drawable.income, "Прочие доходы", Color.rgb(192, 129, 184))
    )


    fun hasInCommon(name: String):Category {

        for (i in expenses) {
            if (i.name == name) {
                return i
            }
        }

        for (i in incomes) {
            if (i.name == name) {
                return i
            }
        }

        return incomes[4]
    }
}

class Category(val srcImage: Int, val name: String, val color: Int)