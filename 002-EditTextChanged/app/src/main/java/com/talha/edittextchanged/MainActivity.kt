package com.talha.edittextchanged

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var mEditTextMessage: EditText
    private lateinit var mTextViewMessage: TextView
    private lateinit var mTextViewUpperMessage: TextView

    private inner class MessageEditTextTextWatcher : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            // TODO("Not yet implemented")
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            s.also { mTextViewMessage.text = it }
        }

        override fun afterTextChanged(s: Editable?) {
            // TODO("Not yet implemented")
        }

    }

    private fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        s.also {
            mTextViewMessage.text = it; mTextViewUpperMessage.text = it.toString().toUpperCase(
            Locale.getDefault())
        }
    }

    private fun initEditTextMessage() {
        mEditTextMessage = findViewById(R.id.mainActivityEditTextMessage)
        // mEditTextMessage.addTextChangedListener(MessageEditTextTextWatcher())


        mEditTextMessage.addTextChangedListener(onTextChanged = ::onTextChanged)
        
    }

    private fun initViews() {
        mTextViewMessage = findViewById(R.id.mainActivityTextViewMessage)
        mTextViewUpperMessage = findViewById(R.id.mainActivityTextViewUpperMessage)
        initEditTextMessage()
        initEditTextUpperMessage()
    }

    private fun beforeTextViewUpperMessageChanged(
        s: CharSequence?,
        start: Int,
        before: Int,
        count: Int
    ) {
        Toast.makeText(this, "writing", Toast.LENGTH_SHORT).show()
    }

    private fun initEditTextUpperMessage() {

        mEditTextMessage.addTextChangedListener(beforeTextChanged = ::beforeTextViewUpperMessageChanged)
    }

    private fun initialize() {
        initViews()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initialize()
    }
}