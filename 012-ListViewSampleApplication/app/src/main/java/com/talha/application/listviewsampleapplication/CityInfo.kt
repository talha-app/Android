package com.talha.application.listviewsampleapplication

data class CityInfo(val name: String, val plate: Int, val phoneCode: Int) {
    override fun toString(): String {
        return name;
    }
}
