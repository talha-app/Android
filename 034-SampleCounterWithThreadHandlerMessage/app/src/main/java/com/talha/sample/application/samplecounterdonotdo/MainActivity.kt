package com.talha.sample.application.samplecounterdonotdo

import android.content.pm.ActivityInfo
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
    private var mHandler = MyHandler(this)
    private lateinit var mDateTimeTimer: Timer
    private var mCounterThread: Thread? = null
    var counter = 1L

    private class MyHandler(val mainActivity: MainActivity) : Handler(Looper.myLooper()!!) {
        private val mWeakReference = WeakReference(mainActivity)
        override fun handleMessage(msg: Message) {
            val mainActivity = mWeakReference.get()!!
            when (msg.what) {
                0 -> {
                    mainActivity.title = msg.obj.toString()
                }
                1 -> handleCounter(mainActivity, msg)
                2-> Toast.makeText(
                    mainActivity,
                    "Counter thread is completed",
                    Toast.LENGTH_SHORT
                ).show()

            }
        }

        private fun handleCounter(mainActivity: MainActivity, msg: Message) {
            var counter = msg.obj.toString().toLong()
            mainActivity.mBinding.mainActivityTextViewCounter.text =
                (counter).toString()
            Toast.makeText(
                mainActivity,
                counter.toString(),
                Toast.LENGTH_SHORT
            ).show()
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

        mCounterThread = Thread {
            try {
                while (true) {
                    val msg = mHandler.obtainMessage(1, ++counter)
                    mHandler.sendMessage(msg)
                    Thread.sleep(1000)
                }
            }catch (ignore:InterruptedException){
                mHandler.sendEmptyMessage(2)
            }
        }.apply { start() }

    }

    private fun initViews(){
        initStartButton()
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
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
        initViews()
    }

    override fun onPause() {
        mCounterThread?.interrupt()
        mDateTimeTimer.cancel()
        super.onPause()
    }

    override fun onResume() {
        initDateTimeTimer()
        if (mCounterThread != null){
            onStartButtonClicked()
        }
        super.onResume()
    }
}