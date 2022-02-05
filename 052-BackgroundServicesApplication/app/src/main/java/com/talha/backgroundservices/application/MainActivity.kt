package com.talha.backgroundservices.application

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.talha.backgroundservices.application.databinding.ActivityMainBinding
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.io.DataInputStream

class MainActivity : AppCompatActivity() {
    private lateinit var mBinding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initialize()
    }

    private fun initialize() {
        initBinding()
        initViews()
    }

    private fun initBinding() {
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
    }


    private fun initViews() {
        mBinding.mainActivityButtonStartService.setOnClickListener { onStartActivityButtonClicked() }
        mBinding.mainActivityButtonGetNumbers.setOnClickListener { onGetNumbersButtonClicked() }
    }

    private fun getNumbersCallback(fileName: String): String {
        val sb = StringBuilder()
        openFileInput(fileName).use {
            val dis = DataInputStream(it)
            try {
                while (true) {
                    sb.append(dis.readInt()).append("-")
                }
            } catch (ignore: Exception) {
            }

        }
        return sb.substring(0, sb.length - 1).toString()
    }

    private fun onGetNumbersButtonClicked() {
        Observable
            .just(mBinding.mainActivityEditTextFileName.text.toString())
            .subscribeOn(Schedulers.io())
            .map { getNumbersCallback(it) }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ mBinding.mainActivityTextViewNumbers.text = it }, {
                Toast.makeText(
                    this, it
                        .message, Toast.LENGTH_LONG
                ).show()
            }, {})
    }

    private fun onStartActivityButtonClicked() {
        val intent = Intent(this, RandomNumberGeneratorService::class.java)
        intent.putExtra("count", mBinding.mainActivityEditTextCount.text.toString().toInt())
        intent.putExtra("min", mBinding.mainActivityEditTextMin.text.toString().toInt())
        intent.putExtra("max", mBinding.mainActivityEditTextMax.text.toString().toInt())
        intent.putExtra("fileName", mBinding.mainActivityEditTextFileName.text.toString())

        synchronized(applicationContext) {
            if (Global.isRunning) {
                Toast.makeText(this, "Global.isRunning", Toast.LENGTH_SHORT).show()
                return
            }
            startService(intent)
            Global.isRunning = true
            finish()
        }


    }
}