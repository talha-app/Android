package com.talha.customdatabindingapplication

enum class DeviceStatus(val str: String) {
    CONNECTED("Bağlı"), DISCONNECTED("Bağlı Değil"), CLOSE("Kapalı");

    override fun toString(): String {
        return str
    }
}