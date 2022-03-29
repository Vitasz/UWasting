package com.example.uwasting.preferences

import android.content.Context
import com.example.uwasting.data.User

const val PREFERENCE_NAME = "SharedPreferenceExample"
const val PREFERENCE_LANGUAGE = "Language"
const val PREFERENCES_USER_NAME = "Username"
const val PREFERENCES_USER_SURNAME = "UserSurname"
const val PREFERENCES_USER_EMAIL = "UserEmail"
const val PREFERENCES_USER_ID = "UserId"

class MyPreference(context: Context) {

    private val preference = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)

    fun getLanguage():String{
        return preference.getString(PREFERENCE_LANGUAGE, "en")!!
    }
    fun setLanguage(Language:String){
        val editor = preference.edit()
        editor.putString(PREFERENCE_LANGUAGE, Language)
        editor.apply()
    }
    fun setUser(user: User){
        val editor = preference.edit()
        editor.putString(PREFERENCES_USER_NAME, user.name)
        editor.putString(PREFERENCES_USER_SURNAME, user.surname)
        editor.putString(PREFERENCES_USER_EMAIL, user.email)
        editor.putInt(PREFERENCES_USER_ID, user.id)
        editor.apply()
    }
    fun getUser():User{
        val user = User()

        user.name = preference.getString(PREFERENCES_USER_NAME, "")!!
        user.surname = preference.getString(PREFERENCES_USER_SURNAME, "")!!
        user.email = preference.getString(PREFERENCES_USER_EMAIL, "")!!
        user.id = preference.getInt(PREFERENCES_USER_ID, -1)

        return user
    }
}