package com.talha.sample.application.sqlite

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.talha.sample.application.sqlite.data.dal.SampleSqliteAppDAL
import com.talha.sample.application.sqlite.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var mBinding: ActivityMainBinding
    private lateinit var mSQLiteAppDAL: SampleSqliteAppDAL

    private fun initButtons() {
        mBinding.mainActivityButtonRegister.setOnClickListener {
            startActivity(
                Intent(
                    this,
                    RegisterActivity::class.java
                )
            )
        }
        mBinding.mainActivityButtonAllUsers.setOnClickListener {
            startActivity(
                Intent(
                    this,
                    AllUsersActivity::class.java
                )
            )
        }
        mBinding.mainActivityButtonExit.setOnClickListener { finish() }
    }

    private fun initViews() {
        initButtons()
    }

    private fun initBinding() {
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
    }

    private fun initialize() {
        initBinding()
        initViews()
        mSQLiteAppDAL = SampleSqliteAppDAL(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        initialize()
        super.onCreate(savedInstanceState)
    }

}