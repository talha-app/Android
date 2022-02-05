package com.talha.customdatabindingapplication.converter

import androidx.databinding.InverseMethod
import java.lang.NumberFormatException

object IntStringConverter {
    @InverseMethod("StringToInt")
    @JvmStatic
    fun IntToString(value: Int) = value.toString()

    @JvmStatic
    fun StringToInt(value: String): Int {
        var result = 0
        try {
            result = value.toInt()

        } catch (ex: NumberFormatException) {

        }
        return result
    }
}