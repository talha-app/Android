package com.talha.application.spinnersampleapplication.application

import android.app.Application
import com.talha.application.spinnersampleapplication.ColorInfo
import com.talha.application.spinnersampleapplication.R

class LoadResourceApplication : Application() {

    companion object {
        private lateinit var mcInstance: LoadResourceApplication
        val INSTANCE: LoadResourceApplication
            get() = mcInstance
        private lateinit var mColors: Array<ColorInfo>
        val Colors: Array<ColorInfo>
            get() = mColors
    }

    override fun onCreate() {
        mcInstance = this
        initColors()
        super.onCreate()
    }

    private fun initColors() {
        mColors = arrayOf(
            ColorInfo(getColor(R.color.white), "Beyaz"),
            ColorInfo(getColor(R.color.red), "Kırmızı"),
            ColorInfo(getColor(R.color.green), "Yeşil"),
            ColorInfo(getColor(R.color.blue), "Mavi")
        )
    }

}