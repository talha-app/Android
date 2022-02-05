package com.talha.app.application

import androidx.databinding.InverseMethod

object IntToStringConverter {
    @InverseMethod("stringToInt")
    @JvmStatic
    fun intToString(value: Int) = value.toString()

    @JvmStatic
    fun stringToInt(str: String) : Int
    {
        var result = -1

        try {
            result = str.toInt()
        }
        catch (ex: NumberFormatException) {

        }
        return result
    }
}