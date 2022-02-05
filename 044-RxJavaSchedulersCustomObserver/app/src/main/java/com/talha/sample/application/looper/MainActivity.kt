package com.talha.sample.application.looper

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.talha.sample.application.looper.databinding.ActivityMainBinding
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
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

    private fun getObserver(): Observer<String> {
        val textViewNumber = mBinding.mainActivityTextViewNumber
        return object : Observer<String> {
            override fun onSubscribe(d: Disposable?) {
            }

            override fun onNext(t: String?) {
                val text = t!!
                textViewNumber.text = text
                Toast.makeText(this@MainActivity, text, Toast.LENGTH_SHORT).show()
            }

            override fun onError(e: Throwable?) {
            }

            override fun onComplete() {
                mBinding.mainActivityButtonGenerate.isEnabled = true
            }
        }
    }

    private fun onGenerateButtonClicked() {
        val count = mBinding.mainActivityEditTextCount.text.toString().toInt()
        val milliseconds = mBinding.mainActivityEditTextSleepMilliseconds.text.toString().toLong()
        var sb = StringBuilder()

        Observable.range(0, count)
            .subscribeOn(Schedulers.io())
            .map { getResult(sb, milliseconds) }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(getObserver())

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