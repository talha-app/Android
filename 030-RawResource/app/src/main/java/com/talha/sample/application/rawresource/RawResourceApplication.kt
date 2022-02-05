package com.talha.sample.application.rawresource

import android.app.Application

class RawResourceApplication : Application() {
    private fun loadRawResources() {
        ResourcesUtil.copyRawResourceToFilesDir(this, R.raw.cities, "cities.txt")
    }

    override fun onCreate() {
        loadRawResources()
        super.onCreate()
    }
}