package com.talha.sample.application.sqlite.data.dal

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.talha.sample.application.sqlite.data.entity.User
import com.talha.sample.application.sqlite.data.repository.UserRepository
import com.talha.sample.application.sqlite.data.util.AndroidIOUtil
import java.io.Closeable
import java.util.*

class SampleSqliteAppDAL(private val context: Context) : Closeable {


    public fun open(): SampleSqliteAppDAL {

        return this
    }

    override fun close() {
        try {

        } catch (ex: Throwable) {
            throw Exception("DataBaseHelper.close Exception")
        }
    }

    fun saveUser(user: User): User {
        return UserRepository.save(user)
    }

    fun findAll(): MutableIterable<User> {
        return UserRepository.findAll()
    }

    fun deleteAll() {
        UserRepository.deleteAll()
    }

    fun deleteById(id: Long?) {
        UserRepository.deleteById(id)
    }

    fun deleteByUser(user: User?) {
        UserRepository.deleteByUser(user)
    }

    fun deleteByUser(str: String?) {
        if (str != null) {
            UserRepository.findUsersByUsernameContains(str)
        }
    }

    fun findByUsername(str: String?): Optional<User> {
        return UserRepository.findUserByUsername(str!!)
    }

    fun findByUsernameContains(str: String?): MutableIterable<User> {
        return UserRepository.findUsersByUsernameContains(str!!)
    }
}