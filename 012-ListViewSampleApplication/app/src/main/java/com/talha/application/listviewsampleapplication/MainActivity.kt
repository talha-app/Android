package com.talha.application.listviewsampleapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog

class MainActivity : AppCompatActivity() {
    private lateinit var mEditTextCityName: TextView
    private lateinit var mEditTextCityPlate: TextView
    private lateinit var mEditTextCityPhoneCode: TextView
    private lateinit var mLisViewNames: ListView
    private lateinit var mNamesAdapter: ArrayAdapter<CityInfo>
    private lateinit var mButtonClear: Button

    private val mNameList: ArrayList<CityInfo> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initiazlize()
    }

    private fun initViews() {
        initEditTexts()
        initListView()
        mButtonClear = findViewById(R.id.mainActivityButtonClear)

    }

    private fun initListView() {
        mLisViewNames = findViewById(R.id.mainActivityListViewNames)
        mNamesAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, mNameList)
        mLisViewNames.adapter = mNamesAdapter
        mLisViewNames.setOnItemClickListener { parent, view, position, id ->
            val (name,plate,phoneCode) =  mNamesAdapter.getItem(position)!!
            val city: CityInfo = (mLisViewNames.getItemAtPosition(position)) as CityInfo
            AlertDialog.Builder(this)
                .setMessage(name)
                .setPositiveButton("OK"){_,_->}
                .show()
        }
    }

    private fun initEditTexts() {
        mEditTextCityName = findViewById(R.id.mainActivityEditTextCityName)
        mEditTextCityPlate = findViewById(R.id.mainActivityEditTextCityPlate)
        mEditTextCityPhoneCode = findViewById(R.id.mainActivityEditTextCityPhoneCode)
    }

    private fun initiazlize() {
        initViews()
    }

    fun onAddWithAdapterButtonClicked(view: View) {
        mButtonClear.isEnabled = true
        val cityInfo = CityInfo(
            mEditTextCityName.text.toString(),
            mEditTextCityPlate.text.toString().toInt(),
            mEditTextCityPhoneCode.text.toString().toInt()
        )
        mNamesAdapter.add(cityInfo)
        mNamesAdapter.notifyDataSetChanged()
    }

    fun onClearButtonClicked(view: View) {
        view.isEnabled = false
        mNamesAdapter.clear()

    }
}