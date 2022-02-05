package com.talha.sample.application.preferences

import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.talha.sample.application.preferences.databinding.ActivityMainBinding
import java.time.LocalDateTime
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    private lateinit var mBinding: ActivityMainBinding
    private lateinit var sharedPrefs: SharedPreferences
    private var mBackgroundColor: Int = Color.WHITE

    private fun onChangeColorButtonClicked() {
        mBackgroundColor =
            Color.rgb(Random.nextInt(255), Random.nextInt(255), Random.nextInt(255))

        mBinding.mainActivityLinearLayoutMain.setBackgroundColor(mBackgroundColor)
    }

    private fun initialize() {
        initBinding()

        mBinding.mainActivityButtonChangeColor.setOnClickListener { onChangeColorButtonClicked() }

        mBinding.mainActivityButtonChangeTitle.setOnClickListener { onChangeTitleButtonClicked() }

        mBinding.mainActivityButtonSecondActivity.setOnClickListener { v ->
            onSecondActivityButtonClicked(
                v
            )
        }

        sharedPrefs = getPreferences(MODE_PRIVATE)
    }

    private fun onSecondActivityButtonClicked(v: View) {
        Intent(this, SecondActivity::class.java).also {
            it.putExtra("datetime", LocalDateTime.now().toString())
            startActivity(it)
        }
    }

    private fun onChangeTitleButtonClicked() {
        title = mBinding.mainActivityEditTextName.text.toString()
    }

    private fun initBinding() {
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        initialize()
        super.onCreate(savedInstanceState)
    }

    override fun onPause() {
        val edit = sharedPrefs.edit()
        edit.putString("title", title.toString())
        edit.putInt("bgColor", mBackgroundColor)
        edit.apply()
        super.onPause()
    }

    override fun onResume() {

        title = sharedPrefs.getString("title", "title default")
        mBinding.mainActivityLinearLayoutMain.setBackgroundColor(
            sharedPrefs.getInt("bgColor", Color.WHITE)
        )
        super.onResume()
    }
}