package com.talha.sample.application.sqlite

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.talha.sample.application.sqlite.data.dal.SampleSqliteAppDAL
import com.talha.sample.application.sqlite.data.entity.User
import com.talha.sample.application.sqlite.databinding.RegisterActivityBinding

class RegisterActivity : AppCompatActivity() {
    private lateinit var mBinding: RegisterActivityBinding
    private lateinit var mSQLiteAppDAL: SampleSqliteAppDAL

    private fun onRegisterButtonClicked() {
        val name = mBinding.registerActivityEditTextName.text.toString()
        val username = mBinding.registerActivityEditTextUsername.text.toString()
        val password = mBinding.registerActivityEditTextPassword.text.toString()

        val user = User(name = name, username = username, password = password)
        try {
            mSQLiteAppDAL.open().use { it.saveUser(user) }
            Toast.makeText(this, user.user_id.toString(), Toast.LENGTH_SHORT).show()
        } catch (ex: Exception) {
            Log.d("onRegisterButtonClicked",ex.message.toString())
        }
    }

    private fun initBinding() {
        mBinding = RegisterActivityBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
    }

    private fun initButtons() {
        mBinding.registerActivityButtonSave.setOnClickListener { onRegisterButtonClicked() }
    }

    private fun initViews() {
        initButtons()
    }

    private fun initialize() {
        initBinding()
        initViews()
        mSQLiteAppDAL = SampleSqliteAppDAL(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initialize()
    }
}