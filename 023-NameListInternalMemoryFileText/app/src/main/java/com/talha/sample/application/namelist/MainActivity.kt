package com.talha.sample.application.namelist

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.io.*

class MainActivity : AppCompatActivity() {
    private lateinit var mEditTextName: EditText
    private lateinit var mListViewName: ListView
    private lateinit var adapter: ArrayAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initialize()
    }

    private fun initialize() {
        initViews()
        loadNames()
    }

    private fun loadNames() {
        openFileInput("editText.dat").use {
            BufferedReader(InputStreamReader(it)).useLines {
                it.forEach {  adapter.add(it)}
            }
        }

    }

    private fun initViews() {
        mEditTextName = findViewById(R.id.mainActivityEditTextName)
        mListViewName = findViewById(R.id.mainActivityListNames)
        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, ArrayList<String>())
        mListViewName.adapter = adapter

    }

    fun onAddButtonClicked(view: View) {
        try {

           openFileOutput("editText.dat", MODE_APPEND).use {
               BufferedWriter(OutputStreamWriter(it)).apply {
                   this.write(mEditTextName.text.toString())
                   this.newLine()
                   this.flush()
               }
           }



        } catch (ex: IOException) {
            Toast.makeText(this, ex.message, Toast.LENGTH_SHORT).show()
        }
    }
}