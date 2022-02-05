package com.talha.application.simplemenuapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.mainactivity_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            R.id.mainActivityMenuItemSettings -> {
                Intent(this,SettingsActivity::class.java).apply {
                    startActivity(this)
                }
            }

            R.id.mainActivityMenuItemAbout -> {
                var dlg = AlertDialog.Builder(this)
                    .setTitle("My alert")
                    .setMessage("Message")
                    .setPositiveButton("Tamam"){_,_-> {}}
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