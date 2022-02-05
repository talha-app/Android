package com.talha.sample.application.samplecounterdonotdo

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.talha.sample.application.samplecounterdonotdo.databinding.ActivityMainBinding
import java.lang.ref.WeakReference
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityMainBinding
    private var mCounter = 0
    private var mHandler = MyHandler(this)
    private lateinit var mDateTimeTimer: Timer

    private class MyHandler(val mainActivity: MainActivity) : Handler(Looper.myLooper()!!) {
        private val mWeakReference = WeakReference(mainActivity)
        override fun handleMessage(msg: Message) {
            val mainActivity = mWeakReference.get()!!
            when (msg.what) {
                0 -> {
                    mainActivity.title = msg.obj.toString()
                }
                1 -> {
                    mainActivity.mBinding.mainActivityTextViewCounter.text =
                        (++mainActivity.mCounter).toString()
                    Toast.makeText(
                        mainActivity,
                        mainActivity.mCounter.toString(),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private fun initStartButton() {
        mBinding.mainActivityButtonStart.setOnClickListener { onStartButtonClicked() }
    }

    private fun initDateTimeTimer() {
        val task = object : TimerTask() {
            override fun run() {
                val now = LocalDateTime.now()
                val dateTimeTimerStr =
                    DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm:ss").format(now)
                val msg = mHandler.obtainMessage(0, dateTimeTimerStr)
                mHandler.sendMessage(msg)
            }

        }
        mDateTimeTimer = Timer().apply {
            scheduleAtFixedRate(task, 0, 1000)
        }
    }

    private fun onStartButtonClicked() {
        mBinding.mainActivityButtonStart.isEnabled = false

        Thread {
            while (true) {
                mHandler.sendEmptyMessage(1)
                Thread.sleep(1000)
            }
        }.start()
    }

    private fun initBinding() {
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initialize()
    }

    private fun initialize() {
        initBinding()
        initDateTimeTimer()
        initStartButton()
    }
}