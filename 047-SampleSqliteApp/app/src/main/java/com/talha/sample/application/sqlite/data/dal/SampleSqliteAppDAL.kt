package com.talha.sample.application.sqlite.data.dal

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.talha.sample.application.sqlite.data.entity.User
import com.talha.sample.application.sqlite.data.repository.UserRepository
import java.io.Closeable
import java.util.*

class SampleSqliteAppDAL(val context: Context) : Closeable {

    companion object {
        private const val DATABASE_NAME = "samplesqliteappsb.sqlite3"
        private const val DATABASE_VERSION = 2
    }

    private lateinit var mSqliteDatabase: SQLiteDatabase
    private val mDatabaseHelper = DatabaseHelper(context, DATABASE_NAME, null, DATABASE_VERSION)

    private inner class DatabaseHelper(
        context: Context, name: String, cursorFactory: SQLiteDatabase
        .CursorFactory?, version: Int
    ) : SQLiteOpenHelper(context, name, cursorFactory, version) {
        override fun onCreate(db: SQLiteDatabase?) {
            db?.execSQL(UserRepository.CREATE_TABLE)
        }

        override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
            db?.execSQL("DROP TABLE IF EXISTS ${UserRepository.TABLE_NAME}")
            onCreate(db)
        }
    }

    public fun open(): SampleSqliteAppDAL {
        mSqliteDatabase = mDatabaseHelper.writableDatabase
        UserRepository.db = mSqliteDatabase
        return this
    }

    override fun close() {
        try {
            mSqliteDatabase.close()
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