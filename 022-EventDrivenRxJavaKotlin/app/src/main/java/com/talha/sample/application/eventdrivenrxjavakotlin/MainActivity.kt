package com.talha.sample.application.eventdrivenrxjavakotlin

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Switch
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.jakewharton.rxbinding4.widget.checkedChanges
import com.jakewharton.rxbinding4.widget.textChanges

class MainActivity : AppCompatActivity() {
    private lateinit var mTextViewResult: TextView
    private lateinit var mEditTextAmount: EditText
    private lateinit var mEditTextRatio: EditText

    private fun initVisibleSwitch() {
        val linearLayoutOperations =
            findViewById<LinearLayout>(R.id.mainActivityLinearLayoutOperations)

        findViewById<Switch>(R.id.mainActivitySwitch)
            .checkedChanges()
            .subscribe {
                linearLayoutOperations.visibility = if (it) View.VISIBLE else View.GONE
            }

    }

    private fun initialize() {
        mEditTextAmount = findViewById(R.id.mainActivityEditTextAmount)
        mEditTextRatio = findViewById(R.id.mainActivityEditTextRatio)
        mTextViewResult = findViewById(R.id.mainActivityTextViewResult)
        initVisibleSwitch()
        initAmountEditText()
        initRatioEditText()
    }

    private fun initAmountEditText() {
        val observable = findViewById<EditText>(R.id.mainActivityEditTextAmount).textChanges()

        observable
            .filter { it.toString().isNotBlank() }
            .doOnNext { mTextViewResult.text = "" }
            .map { it.toString().toDouble() }
            .subscribe { onAmountSubscribe(it) }

        observable.filter { it.toString().isBlank() }
            .subscribe { mTextViewResult.text = "Invalid Amount" }
    }

    private fun initRatioEditText() {
        val observable = findViewById<EditText>(R.id.mainActivityEditTextRatio).textChanges()

        observable
            .filter { it.toString().isNotBlank() }
            .doOnNext { mTextViewResult.text = "" }
            .map { it.toString().toDouble() }
            .subscribe { onRatioSubscribe(it) }

        observable.filter { it.toString().isBlank() }
            .subscribe { mTextViewResult.text = "Invalid Ratio" }

    }

    private fun onAmountSubscribe(amount: Double) {
        try {
            val ratio = mEditTextRatio.text.toString().toDouble()
            val result = amount + amount * ratio
            mTextViewResult.text = "Total : $result"
        } catch (ex: Exception) {
            mTextViewResult.text = "Invalid Values"
        }

    }

    private fun onRatioSubscribe(ratio: Double) {
        try {
            val amount = mEditTextAmount.text.toString().toDouble()
            val result = amount + amount * ratio
            mTextViewResult.text = "Total : $result"
        } catch (ex: Exception) {
            mTextViewResult.text = "Invalid Values"
        }
    }

    private fun onValuesEditTextSubscribe(amount: Double, ratio: Double) {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initialize()
    }
}