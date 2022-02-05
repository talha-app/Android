package com.talha.sample.application.looper

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.talha.sample.application.looper.databinding.ActivityMainBinding
import java.lang.ref.WeakReference
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    private lateinit var mBinding: ActivityMainBinding
    private lateinit var mRandomGeneratorHandler: RandomGeneratorHandler
    private lateinit var mMainHandler: MainHandler

    private class RandomGeneratorHandler(mainActivity: MainActivity) :
        Handler(Looper.myLooper()!!) {
        private val mWeakReference: WeakReference<MainActivity> = WeakReference(mainActivity)

        override fun handleMessage(msg: Message) {
            val param = msg.obj as RandomGeneratorThreadParam
            val mainActivity = mWeakReference.get()!!
            val mainHandler = mainActivity.mMainHandler
            for (i in 1..param.count) {
                val value = Random.nextInt(100)
                mainHandler.sendMessage(mainHandler.obtainMessage(0, value))
                mainHandler.sendMessage(mainHandler.obtainMessage(1, "Result:${value}"))
                Thread.sleep(param.milliseconds)
            }
            mainHandler.sendEmptyMessage(2)
        }
    }

    private class MainHandler(mainActivity: MainActivity) : Handler(Looper.myLooper()!!) {
        private val mWeakReference: WeakReference<MainActivity> = WeakReference(mainActivity)
        val mainActivity = mWeakReference.get()!!
        val textViewNumber = mainActivity.mBinding.mainActivityTextViewNumber
        override fun handleMessage(msg: Message) {
            when (msg.what) {
                0 -> {
                    "${textViewNumber.text.toString()} - ${msg.obj}".also {
                        textViewNumber.text = it
                    }
                }
                1 -> {
                    Toast.makeText(mainActivity, msg.obj.toString(), Toast.LENGTH_SHORT).show()
                }
                2 -> {
                    mainActivity.mBinding.mainActivityButtonGenerate.isEnabled = true
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initialize()
    }

    private fun onGenerateButtonClicked() {
        val count = mBinding.mainActivityEditTextCount.text.toString().toInt()
        val milliseconds = mBinding.mainActivityEditTextSleepMilliseconds.text.toString().toLong()

        val message = mRandomGeneratorHandler.obtainMessage(
            0,
            RandomGeneratorThreadParam(count, milliseconds)
        )
        mBinding.mainActivityButtonGenerate.isEnabled = false
        mRandomGeneratorHandler.sendMessage(message)
    }

    private data class RandomGeneratorThreadParam(val count: Int, val milliseconds: Long)


    private fun runLooper() {
        Looper.prepare()
        mRandomGeneratorHandler = RandomGeneratorHandler(this)
        Looper.loop()
    }

    private fun initLooper() {
        mMainHandler = MainHandler(this)
        Thread { runLooper() }.start()
    }


    private fun initialize() {
        initBinding()
        initGenerateButton()
        initLooper()
    }

    private fun initGenerateButton() {
        mBinding.mainActivityButtonGenerate.setOnClickListener { onGenerateButtonClicked() };

    }

    private fun initBinding() {
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
    }

}