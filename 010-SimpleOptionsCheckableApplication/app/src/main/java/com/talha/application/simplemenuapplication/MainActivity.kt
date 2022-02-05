package com.talha.application.simplemenuapplication

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.LinearLayout
import androidx.appcompat.app.AlertDialog
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    private lateinit var mLinearLayoutMain: LinearLayout

    private fun initialize() {
        initViews()
    }

    private fun initViews() {
        mLinearLayoutMain = findViewById(R.id.mainActivityLinearLayoutMain)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initialize()
    }

    private fun onColorMenuItem(item: MenuItem, color: Int = -1) {
        if (item.isChecked)
            return
        item.isChecked = !item.isChecked
        var backgroundColor = color
        if (color == -1) {
            backgroundColor = Color.rgb(Random.nextInt(256),Random.nextInt(256),Random.nextInt(256))
        }
            mLinearLayoutMain.setBackgroundColor(backgroundColor)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.mainactivity_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.mainActivityMenuItemRed -> {
                onColorMenuItem(item, Color.RED)
            }
            R.id.mainActivityMenuItemGreen -> {
                onColorMenuItem(item, Color.GREEN)
            }
            R.id.mainActivityMenuItemBlue -> {
                onColorMenuItem(item, Color.BLUE)
            }
            R.id.mainActivityMenuItemRandom -> {
                onColorMenuItem(item)
            }
            R.id.mainActivityMenuItemBlack -> {
                onColorMenuItem(item)
            }
            R.id.mainActivityMenuItemAbout -> {
                var dlg = AlertDialog.Builder(this)
                    .setTitle("My alert")
                    .setMessage("Message")
                    .setPositiveButton("Tamam") { _, _ -> {} }
                    .create()
                dlg.show()
            }

            R.id.mainActivityMenuItemExit -> {
                finish()
            }


        }
        return super.onOptionsItemSelected(item)
    }
}