package com.talha.application.tagelement.model

data class PlaceInfo(val name: String, val latitude: Double, val longitude: Double) {

    override fun toString(): String {
        return "PlaceInfo(name='$name', latitude=$latitude, longitude=$longitude)"
    }
}