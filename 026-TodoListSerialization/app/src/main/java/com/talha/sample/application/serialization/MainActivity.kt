package com.talha.sample.application.serialization

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.talha.sample.application.serialization.databinding.ActivityMainBinding
import com.talha.sample.application.serialization.model.Todo
import java.io.*
import java.time.LocalDateTime

const val g_fileName = "todos.dat"

class MainActivity : AppCompatActivity() {
    private lateinit var mBinding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initialize()
    }

    private fun initialize() {

        initBinding()
        initViews()
        loadTodos()
    }

    private fun loadTodos() {
        val adapter =
            ArrayAdapter(this, android.R.layout.simple_list_item_1, ArrayList<Todo>())
        mBinding.mainActivityListTodos.adapter = adapter

        try {
            openFileInput(g_fileName).use {
                try {
                    while (true) {
                        ObjectInputStream(it).apply {
                            val todo = readObject() as Todo
                            adapter.add(todo)
                        }
                    }
                } catch (ignore: EOFException) {
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

        mBinding.mainActivityButtonTodos.setOnClickListener { v -> this.onAddButtonClicked(v) }

    }

    private fun onAddButtonClicked(view: View) {
        try {
            val title = mBinding.mainActivityEditTextTitle.text.toString()
            val description = mBinding.mainActivityEditTextDescription.text.toString()

            openFileOutput(g_fileName, MODE_APPEND).use {
                ObjectOutputStream(it).apply {
                    writeObject(Todo(title, description, LocalDateTime.now()))
                }
            }
            loadTodos()
        } catch (ex: IOException) {
            Toast.makeText(this, ex.message, Toast.LENGTH_SHORT).show()
        }
    }
}