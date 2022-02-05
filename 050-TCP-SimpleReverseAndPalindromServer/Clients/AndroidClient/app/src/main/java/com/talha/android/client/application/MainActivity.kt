package com.talha.android.client.application

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.talha.android.client.application.databinding.ActivityMainBinding
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.io.*
import java.net.Socket

const val REVERSE_PORT = 5050
const val PALINDROME_PORT = 5051

class MainActivity : AppCompatActivity() {
    private lateinit var mBinding: ActivityMainBinding

    private fun initialize() {
        initBinding()
        initViews()

    }

    private fun initViews() {
        initButtons()
    }

    private fun initButtons() {
        mBinding.mainActivityButtonOK.setOnClickListener { onOKButtonClicked() }
    }

    private fun onOKButtonClicked() {
        var host = mBinding.mainActivityEditTextHost.text.toString()
        var port = if (mBinding.mainActivityRadioGroupServers.checkedRadioButtonId == R.id
                .mainActivityRadioButtonReverse
        ) REVERSE_PORT else PALINDROME_PORT
        Observable.just(mBinding.mainActivityEditTextWord.text.toString())
            .subscribeOn(Schedulers.io())
            .map { getResult(it, host, port) }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ mBinding.mainActivitytextViewResult.text = it }, {
                Toast.makeText(
                    this, it
                        .message, Toast.LENGTH_SHORT
                ).show()
            })

    }

    private fun getResult(text: String, host: String, port: Int): String {
        Socket(host, port).use {
            var bufferedWriter = BufferedWriter(OutputStreamWriter(it.getOutputStream()))
            bufferedWriter.write(text + "\r\n");
            bufferedWriter.flush()
            if (port == REVERSE_PORT) {
                var bufferedReader = BufferedReader(InputStreamReader(it.getInputStream()))
                return bufferedReader.readLine()
            }
            var dataOutputStream = DataInputStream(it.getInputStream())
            return if (dataOutputStream.readBoolean()) "Palindrome" else "Not Palindrome"
        }
    }

    private fun initBinding() {
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initialize()
    }
}