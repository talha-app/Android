package com.talha.application.birthdateapplication.global

import java.time.LocalDate
import java.time.temporal.ChronoUnit

data class Person(val name:String, val birthDate: LocalDate?) {
    private val age: Int
    get() = (ChronoUnit.DAYS.between(birthDate,LocalDate.now())/365).toInt()

    override fun toString(): String {
        return "$name / $age / $birthDate"
    }
}