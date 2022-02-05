package com.talha.application.applicationclass

import android.app.Application
import android.widget.Toast

class MyApplication : Application() {
    companion object {
        private lateinit var mcInstance: MyApplication
        val INSTANCE: MyApplication
            get() = mcInstance
    }

    val name = "MyApplication"

    override fun onCreate() {
        mcInstance = this
        super.onCreate()
    }

    override fun onTerminate() {
        Toast.makeText(this,"terminate!", Toast.LENGTH_LONG).show()

        super.onTerminate()
    }
}