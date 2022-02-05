package com.talha.application.simplepersoninfo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.talha.application.simplepersoninfo.model.Person
import com.talha.application.simplepersoninfo.model.global.PersonValidityUtil
import java.lang.Exception

class MainActivity : AppCompatActivity() {
    private lateinit var mEditTextName: EditText
    private lateinit var mEditTextCitizenId: EditText
    private lateinit var mEditTextPhone: EditText
    private lateinit var mTextViewPersonInfo: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initialize()
    }

    private fun isValidCitizenId(): Boolean {
        return mEditTextCitizenId.text.toString().length == 11
    }

    private fun isValidPhone(): Boolean {
        return mEditTextPhone.text.toString().length >= 10
    }

    private fun isNotValidPhone() = !isValidPhone()
    private fun isNotValidCitizenId() = !isValidCitizenId()

    private fun initialize() {
        initializeViews()
    }

    private fun initializeViews() {
        mEditTextName = findViewById(R.id.mainActivityEditTextName)
        mEditTextCitizenId = findViewById(R.id.mainActivityEditTextCitizenId)
        mEditTextPhone = findViewById(R.id.mainActivityEditTextPhone)
        mTextViewPersonInfo = findViewById(R.id.mainActivityTextViewPersonInfo)
    }


    private fun getPerson(): Person? {
        val name = mEditTextName.text.toString()
        val citizenId = mEditTextCitizenId.text.toString()
        val phone = mEditTextPhone.text.toString()
        if (PersonValidityUtil.validate(name, citizenId, phone))
            return Person(name, citizenId, phone)

        return null
    }

    fun onSaveButtonClick(view: View) {
        try {
            val p = getPerson()
            if (p == null) {
                Toast.makeText(this, "Invalid login", Toast.LENGTH_SHORT).show()
                return
            }
            clearViews()

            mTextViewPersonInfo.tag = p
            mTextViewPersonInfo.text = p.name

        } catch (ex: Exception) {
            Toast.makeText(this, ex.message, Toast.LENGTH_SHORT).show()
        }
    }


    fun onCleanButtonClick(view: View) = clearViews()

    private fun clearViews() {
        mEditTextName.text.clear()
        mEditTextCitizenId.text.clear()
        mEditTextPhone.text.clear()
        mTextViewPersonInfo.text = ""
    }

    fun onExitButtonClick(view: View) = finish()
    fun onPersonInfoTextViewClicked(view: View) {
        val person = mTextViewPersonInfo.tag as Person
        Toast.makeText(this, person.toString(), Toast.LENGTH_SHORT).show()
    }
}