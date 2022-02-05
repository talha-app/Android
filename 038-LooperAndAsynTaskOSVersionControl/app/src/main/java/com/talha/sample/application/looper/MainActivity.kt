package com.talha.sample.application.looper

import android.os.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.talha.sample.application.looper.databinding.ActivityMainBinding
import java.lang.ref.WeakReference
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    private lateinit var mBinding: ActivityMainBinding
    private lateinit var mRandomGeneratorHandler: RandomGeneratorHandler
    private lateinit var mMainHandler: MainHandler

    private data class ParamInfo(var count: Int, var milliseconds: Long)

    private inner class RandomGeneratorTask(mainActivity: MainActivity) :
        AsyncTask<ParamInfo, Int, Unit>() {
        private val mWeakReference = WeakReference(mainActivity)

        override fun doInBackground(vararg params: ParamInfo?): Unit {
            var pi = params[0]!!
            val count = pi.count
            val milliseconds = pi.milliseconds
            for (i in 1..count) {
                val value = Random.nextInt(100)
                publishProgress(value)
                Thread.sleep(milliseconds)
            }

        }

        override fun onPostExecute(result: Unit?) {
            val mainActivity = mWeakReference.get()!!
            mainActivity.mBinding.mainActivityButtonGenerate.isEnabled = true

        }

        //value buraya gelir ve ui thread de çalışır
        override fun onProgressUpdate(vararg values: Int?) {
            val mainActivity = mWeakReference.get()!!
            val textViewNumber = mainActivity.mBinding.mainActivityTextViewNumber
            textViewNumber.text = "AsyncTask"
            "${textViewNumber.text.toString()} - ${values[0]}".also {
                textViewNumber.text = it
            }

        }
    }

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

    private fun onGenerateButtonClickedBeforeApiLevel30(count: Int, milliseconds: Long) {
        RandomGeneratorTask(this).execute(ParamInfo(count, milliseconds))
    }

    private fun onGenerateButtonClickedAfterApiLevel30(count: Int, milliseconds: Long) {


        val message = mRandomGeneratorHandler.obtainMessage(
            0,
            RandomGeneratorThreadParam(count, milliseconds)
        )
        mBinding.mainActivityButtonGenerate.isEnabled = false
        mRandomGeneratorHandler.sendMessage(message)
    }

    private fun onGenerateButtonClicked() {
        val count = mBinding.mainActivityEditTextCount.text.toString().toInt()
        val milliseconds = mBinding.mainActivityEditTextSleepMilliseconds.text.toString().toLong()
        if (Build.VERSION.SDK_INT >= 30)
            onGenerateButtonClickedAfterApiLevel30(count, milliseconds)
        else
            onGenerateButtonClickedBeforeApiLevel30(count, milliseconds)
        mBinding.mainActivityButtonGenerate.isEnabled = false
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