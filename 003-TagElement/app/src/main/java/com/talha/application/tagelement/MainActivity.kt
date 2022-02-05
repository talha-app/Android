package com.talha.application.tagelement

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.talha.application.tagelement.model.PlaceInfo
import java.lang.Exception

class MainActivity : AppCompatActivity() {
    private lateinit var mEditTextPlace: EditText
    private lateinit var mEditTextLatitude: EditText
    private lateinit var mEditTextLongitude: EditText
    private lateinit var mTextViewPlace: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initialize()
    }

    private fun getPlaceInfo() :PlaceInfo{
        val name = mEditTextPlace.text.toString()
        val latitude = mEditTextLatitude.text.toString().toDouble()
        val longitude = mEditTextLongitude.text.toString().toDouble()

        return PlaceInfo(name,latitude,longitude)
    }

    private fun initialize() {
        initViews()
    }

    private fun initViews() {
        mEditTextPlace = findViewById(R.id.mainActivityEditTextPlace)
        mEditTextLatitude = findViewById(R.id.mainActivityEditTextLatitude)
        mEditTextLongitude = findViewById(R.id.mainActivityEditTextLongitude)
        mTextViewPlace = findViewById(R.id.mainActivityEditTextViewPlace)

    }

    fun onExitButtonOnClicked(view: View) = finish()

    fun onSaveButtonOnClicked(view: View) {
        try {
            getPlaceInfo().also {
                mTextViewPlace.tag = it
                mTextViewPlace.text = it.toString()
            }
        } catch (ex: Exception) {
            Toast.makeText(this, ex.message, Toast.LENGTH_SHORT).show()
        }

    }

    fun onPlaceTextViewClicked(view: View) {
        (mTextViewPlace.tag as PlaceInfo).apply {
            val message = "${this.name}-${this.latitude}-${this.longitude}"
            Toast.makeText(this@MainActivity,message,Toast.LENGTH_SHORT).show()
        }
    }
}