package com.talha.application.simplepersoninfo.model.global

object PersonValidityUtil {
    private fun isValidLengthEquals(text: String, lenght: Int) = text.length == lenght

    private fun isValidLengthGreater(text: String, lenght: Int) = text.length >= lenght

    fun isValidCitizenId(text: String) = isValidLengthEquals(text, 11)

    fun isValidPhone(text: String) = isValidLengthGreater(text, 10)

    fun isNotValidPhone(text: String) = !isValidPhone(text)
    fun isNotValidCitizenId(text: String) = !isValidCitizenId(text)

    fun validate(name: String, citizenId: String, phone: String): Boolean {

        if (name.isBlank())
            return false

        if (citizenId.isBlank() || isNotValidCitizenId(citizenId))
            return false

        if (phone.isBlank() || isNotValidPhone(citizenId))
            return false

        return true
    }
}