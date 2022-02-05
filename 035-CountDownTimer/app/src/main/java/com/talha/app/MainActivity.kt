package com.talha.app

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.talha.aar.application.R
import com.talha.aar.application.databinding.ActivityMainBinding
import com.talha.aar.samplelibrary.AboutActivity
import com.talha.android.util.application.App
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var mBinding: ActivityMainBinding

      override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initialize()
         }

    private fun initBinding() {
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        mBinding.data = Data()
    }

    private fun initialize() {
        initBinding()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.mainActivityMenuItemDisplay -> Toast.makeText(
                    App.getInstance(),
                    mBinding.data.toString(),
                    Toast.LENGTH_SHORT
            ).show()
            R.id.mainActivityMenuItemOpenAboutActivity -> startActivity(Intent(this,
                    AboutActivity::class.java))
        }
        return super.onOptionsItemSelected(item)
    }

}