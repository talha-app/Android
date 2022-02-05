package com.talha.todoinfoapplication.entity

import java.io.Serializable
import java.time.LocalDateTime

data class TodoInfo(
    var id: Long = 0,
    var title: String = "",
    var description: String = "",
    var startDate: LocalDateTime = LocalDateTime.now(),
    var expectedEndDate: LocalDateTime = startDate.plusDays(10),
    var endDate: LocalDateTime = startDate.plusDays(10),
    var completed: Boolean = false
) : Serializable {
    override fun toString(): String {
        return title
    }
}