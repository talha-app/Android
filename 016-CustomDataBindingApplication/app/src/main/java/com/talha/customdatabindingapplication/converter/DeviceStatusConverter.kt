package com.talha.customdatabindingapplication.converter

import androidx.databinding.InverseMethod
import com.talha.customdatabindingapplication.DeviceStatus

object DeviceStatusConverter {
    @InverseMethod(value = "toDeviceStatus")
    @JvmStatic
    fun toInt(status: DeviceStatus) = status.ordinal

    @JvmStatic
    fun toDeviceStatus(ordinal:Int) = DeviceStatus.values()[ordinal]
}