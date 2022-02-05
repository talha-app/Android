package com.talha.backgroundservices.application

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.widget.Toast
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import java.io.DataOutputStream
import java.util.concurrent.TimeUnit

class RandomNumberGeneratorService : Service() {

    private fun writeFile(dos: DataOutputStream, value: Int) {
        dos.writeInt(value)
    }

    override fun onBind(intent: Intent): IBinder {
        throw NotImplementedError("Not implemented")
    }

    override fun onCreate() {
        Toast.makeText(this, "RandomNumberGeneratorService.onCreate", Toast.LENGTH_LONG).show()

        super.onCreate()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Toast.makeText(this, "RandomNumberGeneratorService.onStartCommand", Toast.LENGTH_LONG)
            .show()
        val intent = intent!!
        val fileName = intent.getStringExtra("fileName")
        val min = intent.getIntExtra("min", 0)
        val max = intent.getIntExtra("max", 100)
        var count = intent.getIntExtra("count", 100)
        val fos = openFileOutput(fileName, MODE_APPEND)
        val dos = DataOutputStream(fos)

        fun mapCallback(): Int {
            count--
            val num = kotlin.random.Random.nextInt(min, max + 1)
            writeFile(dos, num)
            return num
        }

        Observable.interval(1, TimeUnit.SECONDS).takeWhile { count != 0 }
            .map { mapCallback() }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ Toast.makeText(this, "$it", Toast.LENGTH_SHORT).show() },
                {},
                {
                    stopSelf()
                    fos.close()
                })
        return START_REDELIVER_INTENT
    }

    override fun onDestroy() {
        Toast.makeText(this, "RandomNumberGeneratorService.onDestroy", Toast.LENGTH_LONG).show()
        synchronized(applicationContext) {
            Global.isRunning = false
        }
        super.onDestroy()
    }

}



