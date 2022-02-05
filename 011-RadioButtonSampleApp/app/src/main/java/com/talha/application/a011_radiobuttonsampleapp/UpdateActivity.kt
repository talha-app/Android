package com.talha.application.a011_radiobuttonsampleapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView

class UpdateActivity : AppCompatActivity() {
    private lateinit var mTextViewActivity :TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update)
        initialize()
    }

    private fun initialize() {
        mTextViewActivity = findViewById(R.id.updateActivityTextView)
        val text = intent.getStringExtra("object") as String
        mTextViewActivity.text = text
    }

    fun onTextViewClicked(view: View) {
        Thread.sleep(2000)
        setResult(1234)
        finish()
    }
}