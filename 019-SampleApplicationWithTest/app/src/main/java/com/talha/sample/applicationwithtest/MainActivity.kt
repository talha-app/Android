package com.talha.sample.applicationwithtest

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private lateinit var mListViewNames: ListView

    private fun initNames(names: MutableList<String>) {
        names.add("Ali")
        names.add("Veli")
        names.add("Selami")
    }

    private fun initialize() {
        initViews()
    }

    private fun initViews() {
        initListViews()
    }

    private fun initListViews() {
        mListViewNames = findViewById(R.id.mainActivityListViewNames)
        val names = ArrayList<String>()
        initNames(names);
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, names)
        mListViewNames.adapter = adapter

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initialize()
    }
}