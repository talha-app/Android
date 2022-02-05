package com.talha.sample.application.cachedir

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.talha.sample.application.cachedir.databinding.ActivityMainBinding
import java.io.*

class MainActivity : AppCompatActivity() {
    private lateinit var mBinding: ActivityMainBinding
    private lateinit var mFileCache: File
    private lateinit var mFileNames: File


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initialize()
    }

    private fun initialize() {
        mFileCache = cacheDir
        mFileNames = File(mFileCache, "names.txt")
        initBinding()
        initViews()
        loadNames()
    }

    private fun loadNames() {
        val adapter =
            ArrayAdapter(this, android.R.layout.simple_list_item_1, ArrayList<String>())
        mBinding.mainActivityListNames.adapter = adapter
        try {
            FileInputStream(mFileNames).use { fis ->
                BufferedReader(InputStreamReader(fis)).useLines { br ->
                    br.forEach { adapter.add(it) }
                }
            }
        } catch (ignore: FileNotFoundException) {
            println()
        }


    }

    private fun initBinding() {
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
    }


    private fun initViews() {


        mBinding.mainActivityButtonAdd.setOnClickListener { v -> onAddButtonClicked(v) }

    }

    fun onAddButtonClicked(view: View) {
        try {
            FileOutputStream(mFileNames, true).use {
                BufferedWriter(OutputStreamWriter(it)).apply {
                    this.write(mBinding.mainActivityEditTextName.text.toString())
                    this.newLine()
                    this.flush()
                }
            }
            loadNames()
        } catch (ex: IOException) {
            Toast.makeText(this, ex.message, Toast.LENGTH_SHORT).show()
        }
    }
}