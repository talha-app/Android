package com.talha.app.application.viewmodel

import java.io.Serializable
import java.time.LocalDateTime

data class ResultInfo(var result: Int = 0, var dateTime: LocalDateTime = LocalDateTime.now()) :
    Serializable