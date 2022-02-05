package com.talha.sample.application.serialization.model

import java.io.Serializable
import java.time.LocalDateTime

data class Todo(
    var title: String = "",
    var description: String = "",
    var lastUpdateDate: LocalDateTime
) : Serializable {
    override fun toString(): String {
        return title
    }
}
