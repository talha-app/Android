package com.talha.sample.application.sqlite.application

import android.app.Application
import com.talha.sample.application.sqlite.data.objectbox.ObjectBox

class SampleObjectBoxApplication : Application() {
    override fun onCreate() {
        ObjectBox.initialize(this)
        super.onCreate()
    }
}