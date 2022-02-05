package com.talha.customdatabindingapplication.viewmodel

import com.talha.customdatabindingapplication.DeviceStatus
import java.time.LocalDate

data class Device(
    var name: String = "",
    var host: String = "",
    var port: Int = 0,
    var status: DeviceStatus = DeviceStatus.CLOSE,
    var registerDate: LocalDate = LocalDate.now()
)