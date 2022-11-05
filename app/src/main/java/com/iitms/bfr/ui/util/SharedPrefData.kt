package com.iitms.sdcloud.ui.util

import android.content.SharedPreferences
import javax.inject.Inject

class SharedPrefData @Inject constructor() {

    companion object{

    }

    @Inject
    lateinit var spEdit: SharedPreferences.Editor

    @Inject
    lateinit var sharedPref: SharedPreferences


    fun saveData(key: String, value: String) {
        spEdit.putString(key, value)
        spEdit.apply()
    }
    fun removeData() {
        spEdit.clear()
        spEdit.apply()
    }

    fun saveData(key: String, value: Int) {
        spEdit.putInt(key, value)
        spEdit.apply()
    }

    fun saveData(key: String, value: Boolean) {
        spEdit.putBoolean(key, value)
        spEdit.apply()
    }

    fun getString(key: String) : String {
        return sharedPref.getString(key, "")!!
    }

    fun getInt(key: String) : Int{
        return sharedPref.getInt(key, 0)
    }

    fun clear() {
        return sharedPref.edit().clear().apply()
    }

}