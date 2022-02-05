package com.talha.todoinfoapplicationmultilayer

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.talha.aar.application.R
import com.talha.aar.application.databinding.ActivityMainBinding
import com.talha.todoinfo.application.data.service.TodoApplicationDataService
import com.talha.todoinfoapplication.entity.TodoInfo
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var mListViewTodos: ListView
    private lateinit var mTodosListAdapter: ArrayAdapter<TodoInfo>
    private lateinit var mBinding: ActivityMainBinding

    @Inject
    lateinit var todoApplicationService: TodoApplicationDataService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initialize()
    }

    private fun initViews() {
        initTodosListView()
    }


    private fun initTodosListView() {
        mListViewTodos = findViewById(R.id.mainActivityListViewTodos)
        mTodosListAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, ArrayList())
        mListViewTodos.adapter = mTodosListAdapter
    }

    private fun initBinding() {
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        mBinding.todoInfo = TodoInfo()
    }

    private fun initialize() {
        initBinding()
        initViews()
    }

    private fun saveTodo() {

        try {
            todoApplicationService.saveTodo(mBinding.todoInfo!!)
            mBinding.todoInfo = TodoInfo()
            loadTodo()
        } catch (ex: Exception) {
            Toast.makeText(this, ex.message, Toast.LENGTH_SHORT).show()
        }

    }

    private fun loadTodo() {

        try {
            mTodosListAdapter.clear()
            todoApplicationService.findAllTodos().forEach { mTodosListAdapter.add(it) }
        } catch (ex: Exception) {
            Toast.makeText(this, ex.message, Toast.LENGTH_SHORT).show()
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.mainActivityMenuItemSaveTodo -> saveTodo()
            R.id.mainActivityMenuItemLoad -> loadTodo()
            R.id.mainActivityMenuItemClear -> clearList()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun clearList() {
        mTodosListAdapter.clear()
    }

}