package com.example.flowmodoroapp.data

import android.app.Activity
import android.content.Context

class MySharedPreference(private val activity: Activity) {
    fun saveInSharedPref(value:Boolean){
        val sharedPref = activity.getPreferences(Context.MODE_PRIVATE)
        with(sharedPref.edit()){
            putBoolean("isDarkThemeOn", value)
            apply()
        }
    }


    fun getSharedPref():Boolean{
        val sharedPref = activity.getPreferences(Context.MODE_PRIVATE)
        val isDarkThemeOn = sharedPref.getBoolean("isDarkThemeOn", false)
        return isDarkThemeOn
    }
}