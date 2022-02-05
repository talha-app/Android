package com.talha.app

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import androidx.appcompat.app.AppCompatActivity
import com.talha.aar.application.databinding.ActivitySplashBinding

class SplashActivity : AppCompatActivity() {
    private lateinit var mBinding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initialize()
    }

    private fun initialize() {
        initBinding()
        initCountDownTimer()
    }

    private fun initBinding() {
        mBinding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
    }

    private fun initCountDownTimer() {
        object : CountDownTimer(10000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                mBinding.splashActivityTextViewCounter.text =
                    (millisUntilFinished / 1000).toString()
            }

            override fun onFinish() {
                startActivity(Intent(this@SplashActivity, MainActivity::class.java))
                this@SplashActivity.finish()
            }

        }.start()

    }
}