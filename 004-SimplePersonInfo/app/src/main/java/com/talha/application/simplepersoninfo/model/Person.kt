package com.talha.application.simplepersoninfo.model

data class Person(val name: String, val citizenId: String, val phone: String) {
    override fun toString(): String {
        return "Person(name='$name', citizenId='$citizenId', phone='$phone')"
    }
}