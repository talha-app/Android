package com.talha.application.spinnersampleapplication

import android.graphics.Color

data class ColorInfo (val color: Int,var name:String ){

    override fun toString(): String  = name
}