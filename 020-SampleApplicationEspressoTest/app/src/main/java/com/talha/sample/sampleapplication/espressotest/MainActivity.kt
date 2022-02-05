package com.talha.sample.sampleapplication.espressotest

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private lateinit var mEditTextMessage: EditText

    private fun initViews() {
        mEditTextMessage = findViewById(R.id.mainActivityEditTextMessage)
    }

    private fun initialize() {
        initViews()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initialize()
    }

    fun onReverseButtonClicked(view: View) {
        val message = mEditTextMessage.text.toString().reversed()
        mEditTextMessage.setText(message)

    }

    fun onOpenMessageDetailActivityButtonClicked(view: View) {
        val message = mEditTextMessage.text.toString()
        Intent(this, OtherActivity::class.java).also {
            it.putExtra("message", message)
            startActivity(it)
        }
    }
}