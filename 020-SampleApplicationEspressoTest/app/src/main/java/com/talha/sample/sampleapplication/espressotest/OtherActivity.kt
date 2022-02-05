package com.talha.sample.sampleapplication.espressotest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import java.util.*

class OtherActivity : AppCompatActivity() {
    private lateinit var mEditTextMessage: EditText

    private fun initViews()
    {
        mEditTextMessage = findViewById(R.id.otherActivityEditTextMessage)
        mEditTextMessage.setText(intent.getStringExtra("message")?.toUpperCase(Locale.getDefault()))
    }

    private fun initialize()
    {
        initViews()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_other)
        initialize()
    }
}