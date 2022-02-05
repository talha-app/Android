package com.talha.customdatabindingapplication.converter

import androidx.databinding.InverseMethod
import java.time.LocalDate
import java.time.format.DateTimeFormatter

object LocalDateStringConverter {
    @JvmStatic
    private val pattern: DateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")

    @InverseMethod(value = "toLocalDate")
    @JvmStatic
    fun toString(date: LocalDate) = date.format(pattern)

    @JvmStatic
    fun toLocalDate(str: String): LocalDate? {
        try {
            return LocalDate.parse(str, pattern)
        } catch (ex: Exception){}
        return LocalDate.now()
    }
}