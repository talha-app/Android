package com.talha.sample.samplecounterwithexecutors

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.talha.sample.samplecounterwithexecutors.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import java.lang.ref.WeakReference
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.concurrent.ExecutorService
import java.util.concurrent.Future
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var mBinding: ActivityMainBinding
    private val mHandler = MyHandler(this)
    private var mStop = false
    private var mCounter = 1L;
    @Inject
    lateinit var threadPool: ExecutorService
    private lateinit var mCounterFuture: Future<*>

    private class MyHandler(val mainActivity: MainActivity) : Handler(Looper.myLooper()!!) {
        private val mWeakReference = WeakReference(mainActivity)
        override fun handleMessage(msg: Message) {
            val mainActivity = mWeakReference.get()!!
            when (msg.what) {
                1 -> handleCounter(mainActivity, msg)
                2 -> Toast.makeText(
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
        Observable.interval(1, TimeUnit.SECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .takeUntil { mStop }
            .subscribe({ dateTimeTimerCallback() }, {}, { dateTimeTimerCancelledCallback() })

    }


    private fun dateTimeTimerCancelledCallback() {
        Toast.makeText(this, "Timer sonlandırıldı", Toast.LENGTH_LONG).show()
    }

    private fun dateTimeTimerCallback() {
        val dateTimeTimerStr =
            DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm:ss").format(LocalDateTime.now())
        title = dateTimeTimerStr
    }

    private fun onStartButtonClicked() {
        mBinding.mainActivityButtonStart.isEnabled = false

        mCounterFuture = threadPool.submit {
            try {
                while (true) {
                    val msg = mHandler.obtainMessage(1, ++mCounter)
                    mHandler.sendMessage(msg)
                    Thread.sleep(1000)
                }
            } catch (ignore: InterruptedException) {
                mHandler.sendEmptyMessage(2)
            }
        }

    }

    private fun initViews() {
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
        if (this::mCounterFuture.isInitialized)
            mCounterFuture.cancel(true)

        mStop = true
        super.onPause()
    }

    override fun onResume() {
        initDateTimeTimer()
        if (this::mCounterFuture.isInitialized)
            onStartButtonClicked()

        super.onResume()
    }
}