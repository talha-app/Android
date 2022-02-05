package com.talha.sample.application.looper

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.talha.sample.application.looper.databinding.ActivityMainBinding
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    private lateinit var mBinding: ActivityMainBinding

    private fun getResult(sb: StringBuilder, milliseconds: Long): String {
        val value = Random.nextInt(100)
        sb.append(value).append('-')
        Thread.sleep(milliseconds)
        return sb.substring(0, sb.length - 1).toString()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initialize()
    }

    private fun onGenerateButtonClicked() {
        val count = mBinding.mainActivityEditTextCount.text.toString().toInt()
        val milliseconds = mBinding.mainActivityEditTextSleepMilliseconds.text.toString().toLong()
        val textViewNumber = mBinding.mainActivityTextViewNumber
        var sb = StringBuilder()

        Observable.range(0, count)
            .subscribeOn(Schedulers.single())
            .map { getResult(sb, milliseconds) }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { title = it },
                {},
                { mBinding.mainActivityButtonGenerate.isEnabled = true })
        mBinding.mainActivityButtonGenerate.isEnabled = false
    }

    private fun initialize() {
        initBinding()
        initGenerateButton()
    }

    private fun initGenerateButton() {
        mBinding.mainActivityButtonGenerate.setOnClickListener { onGenerateButtonClicked() };

    }

    private fun initBinding() {
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
    }

}