package com.talha.application.togglebuttoncheckboxswitch

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.CheckBox
import android.widget.Switch
import android.widget.Toast
import android.widget.ToggleButton

class MainActivity : AppCompatActivity() {
    private lateinit var mToggleButtonConnect: ToggleButton
    private lateinit var mSwitchAllow: Switch
    private lateinit var mCheckBoxAccept: CheckBox

    private fun initViews() {
        mToggleButtonConnect = findViewById(R.id.mainActivityToggleButtonConnect)
        mToggleButtonConnect.setOnCheckedChangeListener { _, isChecked ->
            var connectionMessage = if (isChecked) "Cihaz Bağlı" else "cihaz bağlı değil"
            Toast.makeText(this, connectionMessage, Toast.LENGTH_SHORT).show()
        }
        mSwitchAllow = findViewById(R.id.mainActivitySwitchAllow)
        mSwitchAllow.setOnCheckedChangeListener { _, isChecked ->
            var allowMessage = if (isChecked) "İzin verildi" else "izin verilmedi"
            Toast.makeText(this, allowMessage, Toast.LENGTH_SHORT).show()
        }
        mCheckBoxAccept = findViewById(R.id.mainActivityCheckBoxAccept)
        mCheckBoxAccept.setOnCheckedChangeListener { _, isChecked ->
            var acceptMessage = if (isChecked) "Kabul Edildi" else "reddedildi"
            Toast.makeText(this, acceptMessage, Toast.LENGTH_SHORT).show()
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initialize()
    }

    private fun initialize() {
        initViews()
    }

    fun onOKButtonClicked(view: View) {
        Toast.makeText(this, "Test", Toast.LENGTH_SHORT).show()
    }
}