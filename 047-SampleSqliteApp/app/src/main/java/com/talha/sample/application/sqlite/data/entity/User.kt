package com.talha.sample.application.sqlite.data.entity

import java.time.LocalDateTime

data class User(
    var user_id: Long = 0,
    var name: String = "",
    var username: String = "",
    var password: String = "",
    var registerDate: LocalDateTime = LocalDateTime.now(),
) {
    override fun toString(): String {
        return username
    }
}
