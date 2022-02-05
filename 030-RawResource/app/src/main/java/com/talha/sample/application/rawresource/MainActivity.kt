package com.talha.sample.application.rawresource

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.talha.sample.application.rawresource.databinding.ActivityMainBinding
import java.io.BufferedReader
import java.io.FileNotFoundException
import java.io.IOException
import java.io.InputStreamReader
import java.nio.charset.StandardCharsets

class MainActivity : AppCompatActivity() {
    private lateinit var mBinding: ActivityMainBinding

    private fun initBinding()
    {
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initialize()
    }

    private fun initialize() {
        initBinding()
        initCitiesSpinner()

    }

    private fun initCitiesSpinner() {
        try {
            openFileInput("cities.txt").use {
                val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, ArrayList<String>())
                BufferedReader(InputStreamReader(it, StandardCharsets.UTF_8)).useLines {seq ->
                    seq.forEach { line -> adapter.add(line) }
                }
                mBinding.mainActivitySpinnerCities.adapter = adapter
            }
        }
        catch (ignore: FileNotFoundException) {
            ResourcesUtil.copyRawResourceToFilesDir(this, R.raw.cities, "cities.txt")
        }
        catch (ignore: Exception) {
            Toast.makeText(this, ignore.message, Toast.LENGTH_LONG).show()
        }
    }
}