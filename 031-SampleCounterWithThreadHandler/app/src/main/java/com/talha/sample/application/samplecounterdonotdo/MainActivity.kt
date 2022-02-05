package com.talha.sample.application.samplecounterdonotdo

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.talha.sample.application.samplecounterdonotdo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityMainBinding
    private var mCounter = 0
    private var mHandler = Handler(Looper.myLooper()!!)
    private fun initStartButton() {
        mBinding.mainActivityButtonStart.setOnClickListener { onStartButtonClicked() }
    }

    private fun onStartButtonClicked()
    {
        mBinding.mainActivityButtonStart.isEnabled = false

        Thread { //Dikkat activity yok edildiğinde bu thread'in de sonlandırılması gerekir. Henüz yapmadık
            while (true) {
                mHandler.post { mBinding.mainActivityTextViewCounter.text = (++mCounter).toString()
                    Toast.makeText(this, mCounter.toString(), Toast.LENGTH_SHORT).show() }
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
        initStartButton()
    }
}