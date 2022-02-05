package com.talha.customdatabindingapplication

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.talha.genericapplication.R
import com.talha.genericapplication.databinding.ActivityMainBinding
import com.talha.customdatabindingapplication.viewmodel.Device

class MainActivity : AppCompatActivity() {
    private lateinit var mBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initialize()
    }

    private fun initBinding() {
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        mBinding.device = Device(port = 24)
    }

    private fun initialize() {
        initBinding()
        initviews()
    }

    private fun initviews() {
        findViewById<Spinner>(R.id.mainActivitySpinner).also {
            it.adapter = ArrayAdapter(this,android.R.layout.simple_spinner_item,DeviceStatus.values())
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.mainActivityMenuItemDisplay -> Toast.makeText(
                this,
                mBinding.device.toString(),
                Toast.LENGTH_SHORT
            ).show()
        }
        return super.onOptionsItemSelected(item)
    }

}