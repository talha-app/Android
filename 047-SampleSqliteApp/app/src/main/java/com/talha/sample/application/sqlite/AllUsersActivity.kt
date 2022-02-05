package com.talha.sample.application.sqlite

import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.talha.sample.application.sqlite.data.dal.SampleSqliteAppDAL
import com.talha.sample.application.sqlite.data.entity.User
import com.talha.sample.application.sqlite.databinding.ActivityAllUsersBinding
import java.util.*
import kotlin.collections.ArrayList

class AllUsersActivity : AppCompatActivity() {
    private lateinit var mBinding: ActivityAllUsersBinding
    private lateinit var mSQLiteAppDAL: SampleSqliteAppDAL

    private fun loadUsers() { //Asenkron çalışmalı
        try {
            mBinding.allUsersActivityListViewUsers.also { users ->
                mSQLiteAppDAL.open().use {
                    val userList = it.findAll().toList()
                    if (userList.isNotEmpty())
                        users.adapter = ArrayAdapter(
                            this, android.R.layout.simple_list_item_1, userList
                        )
                }

            }
        } catch (ex: Throwable) {
            Log.d("loadUsers", ex.message!!)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initialize()
    }

    private fun initBinding() {
        mBinding = ActivityAllUsersBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
    }

    private fun initialize() {
        initBinding()
        mSQLiteAppDAL = SampleSqliteAppDAL(this)
        initViews()
    }

    private fun initViews() {
        initButtons()
        initUserList()
    }

    private fun initUserList() {
        loadUsers()
    }

    private fun onPossitiveButtonClicked() {
        try {
            mBinding.allUsersActivityListViewUsers.also { users ->
                mSQLiteAppDAL.open().use {
                    it.deleteAll()
                    users.adapter = ArrayAdapter(
                        this, android.R.layout.simple_list_item_1, it.findAll().toList()
                    )
                }

            }
        } catch (ex: Throwable) {
            Log.d("loadUsers", ex.message!!)
        }
    }

    private fun onDeleteAllUsersButtonClicked() {
        val dlg = AlertDialog.Builder(this)
            .setTitle("Dikkat")
            .setMessage("Silmek istediğinize emin misiniz")
            .setPositiveButton("Evet") { _, _ -> onPossitiveButtonClicked() }
            .setNegativeButton("Hayır") { _, _ -> }
            .create()
        dlg.show()
    }

    private fun onSearchButtonClicked() {
        var id = mBinding.allUsersActivityRadioGroupSelection.checkedRadioButtonId
        val username = mBinding.allUsersActivityEditTextUsername.text.toString()
        var userList = ArrayList<User>()
        try {
            mBinding.allUsersActivityListViewUsers.also { users ->
                mSQLiteAppDAL.open().use {
                    var user: Optional<User>
                    if (id == R.id.allUsersActivityRadioButtonUsername) {
                        user = it.findByUsername(username)
                        if (user.isPresent)
                            userList.add(user.get())
                    } else if (id == R.id.allUsersActivityRadioButtonUsernameContains) {
                        userList = it.findByUsernameContains(username) as ArrayList<User>
                    }
                    users.adapter = ArrayAdapter(
                        this, android.R.layout.simple_list_item_1, userList
                    )
                }
            }
        } catch (ex: Throwable) {
            Log.d("loadUsers", ex.message!!)
        }
    }

    private fun initButtons() {
        mBinding.allUsersActivityButtonDeleteAllUsers.setOnClickListener { onDeleteAllUsersButtonClicked() }

        mBinding.allUsersActivityButtonSearch.setOnClickListener { onSearchButtonClicked() }
    }
}