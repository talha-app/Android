package com.talha.sample.application.preferences

import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.talha.sample.application.preferences.databinding.ActivitySecondBinding

class SecondActivity : AppCompatActivity() {
    private lateinit var mBinding: ActivitySecondBinding
    private lateinit var sharedPrefs: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initialize()
    }

    private fun initialize() {
        initBinding()
        initSharedPrefs()
    }

    private fun initSharedPrefs() {
        sharedPrefs = getPreferences(MODE_PRIVATE)
        title = sharedPrefs.getString("title", "")
        val bgColor = sharedPrefs.getInt("bgColor", Color.BLUE)
        mBinding.secondActivityConstraintLayout.setBackgroundColor(bgColor)
        AlertDialog.Builder(this)
            .setMessage(intent.getStringExtra("datetime"))
            .setPositiveButton("OK") { _, _ ->
                { }
            }
            .show()
    }

    private fun initBinding() {
        mBinding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
    }
}