package com.talha.sample.samplecounterwithexecutors

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.talha.sample.samplecounterwithexecutors.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.lang.ref.WeakReference
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import java.util.concurrent.ExecutorService
import java.util.concurrent.Future
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import kotlin.collections.ArrayList
import kotlin.random.Random

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var mBinding: ActivityMainBinding
    private val mHandler = MyHandler(this)
    private var mStop = false

    private data class ParamInfo(val count: Int, val minLenght: Int, val maxLenght: Int)

    @Inject
    lateinit var threadPool: ExecutorService
    private lateinit var mRandomGeneratorInitFuture: Future<*>

    private class MyHandler(val mainActivity: MainActivity) : Handler(Looper.myLooper()!!) {
        private val mWeakReference = WeakReference(mainActivity)

        override fun handleMessage(msg: Message) {
            when (msg.what) {
                2 -> "${mainActivity.mBinding.mainActivityTextViewWaiting.text}.".apply {
                    mainActivity.mBinding.mainActivityTextViewWaiting.text = this
                }
            }
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

    private fun generateRandomStringEN(n: Int): String {
        val sb = StringBuilder(n)
        fun getChar() = when (Random.nextBoolean()) {
            true -> Random.nextInt('A'.toInt(), ('Z' + 1).toInt()).toChar()
            else -> Random.nextInt('a'.toInt(), ('z' + 1).toInt()).toChar()
        }
        for (i in 1..n)
            sb.append(getChar())

        return sb.toString()
    }

    private fun randomGeneratorThreadCallback(
        timer: Timer,
        paramInfo: ParamInfo,
    ): ArrayList<String> {
        return ArrayList<String>().apply {
            timer.schedule(object : TimerTask() {
                override fun run() {
                    mHandler.sendEmptyMessage(2)
                }
            }, 0, 1000)
            for (i in 1..paramInfo.count) {
                this.add(
                    generateRandomStringEN(
                        Random.nextInt(
                            paramInfo.minLenght,
                            paramInfo.maxLenght
                        )
                    )
                )
                Thread.sleep(Random.nextLong(2000))
            }
        }
    }

    private suspend fun runRandomGenerator(paramInfo: ParamInfo) {
        val timer = Timer()


        val deferred = GlobalScope.async {
            randomGeneratorThreadCallback(timer, paramInfo)
        }
        val list = deferred.await()
        ArrayAdapter(
            this@MainActivity,
            android.R.layout.simple_list_item_1,
            list
        ).apply { mBinding.mainActivityListViewTexts.adapter = this }
        mBinding.mainActivityButtonStart.isEnabled = true
        timer.cancel()

    }

    private fun onStartButtonClicked() {
        mBinding.mainActivityButtonStart.isEnabled = false
        val count = mBinding.mainActivityEditTextCount.text.toString().toInt()
        val minLenght = mBinding.mainActivityEditTextMinLength.text.toString().toInt()
        val maxLenght = mBinding.mainActivityEditTextMaxLength.text.toString().toInt()
        mBinding.mainActivityTextViewWaiting.text = ""
        GlobalScope.launch(Dispatchers.Main) {
            runRandomGenerator(ParamInfo(count, minLenght, maxLenght))

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
        if (this::mRandomGeneratorInitFuture.isInitialized)
            mRandomGeneratorInitFuture.cancel(true)
        mBinding.mainActivityButtonStart.isEnabled = true
        mStop = true
        super.onPause()
    }

    override fun onResume() {
        initDateTimeTimer()
        super.onResume()
    }
}