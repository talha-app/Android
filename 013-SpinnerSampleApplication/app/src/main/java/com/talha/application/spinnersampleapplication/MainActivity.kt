package com.talha.application.spinnersampleapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*

class MainActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    private lateinit var mColors: Array<ColorInfo>
    private lateinit var mTextViewColor: TextView
    private lateinit var mSpinnerColor: Spinner
    private lateinit var mArrayAdapter: ArrayAdapter<ColorInfo>
    private lateinit var mLinearLayoutMain: LinearLayout


    private fun initColorSpinner() {
        mSpinnerColor = findViewById(R.id.mainActivitySpinnerColors)
        mArrayAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, mColors)
        mSpinnerColor.adapter = mArrayAdapter
        mSpinnerColor.onItemSelectedListener = this


    }

    private fun initViews() {
        mLinearLayoutMain = findViewById(R.id.mainActivityLinearLayout)
        initColorSpinner()
        mTextViewColor = findViewById(R.id.mainActivityTextViewColor)
    }

    private fun initialize() {
        initColors()
        initViews()
    }

    private fun initColors() {
        mColors = arrayOf(
            ColorInfo(getColor(R.color.white), "Beyaz"),
            ColorInfo(getColor(R.color.red), "Kırmızı"),
            ColorInfo(getColor(R.color.green), "Yeşil"),
            ColorInfo(getColor(R.color.blue), "Mavi")
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initialize()
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        mLinearLayoutMain.setBackgroundColor(mColors[position].color)
        val c:ColorInfo = mSpinnerColor.getItemAtPosition(position) as ColorInfo
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        // TODO("Not yet implemented")
    }
}