package com.talha.sample.application.sqlite.data.repository

import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import com.talha.sample.application.sqlite.data.entity.User
import java.time.LocalDateTime
import java.util.*
import kotlin.collections.ArrayList

object UserRepository {
    const val TABLE_NAME = "users"
    const val ID = "user_id"
    const val NAME = "name"
    const val USERNAME = "username"
    const val PASSWORD = "password"
    const val REGISTER_DATE = "register_date"
    const val CREATE_TABLE = "CREATE TABLE $TABLE_NAME" +
            " ($ID integer primary key autoincrement," +
            "$NAME text not null," +
            "$USERNAME text not null unique," +
            "$PASSWORD text not null," +
            "$REGISTER_DATE text not null);"

    private fun getContentValues(user: User): ContentValues {
        val contentValues = ContentValues()
        contentValues.put(NAME, user.name)
        contentValues.put(USERNAME, user.username)
        contentValues.put(PASSWORD, user.password)
        contentValues.put(REGISTER_DATE, user.registerDate.toString())
        return contentValues
    }

    private fun getUser(cursor: Cursor): User {
        val userId = cursor.getLong(0)
        val name = cursor.getString(1)
        val username = cursor.getString(2)
        val password = cursor.getString(3)
        val registerDate = LocalDateTime.parse(cursor.getString(4))
        return User(userId, name, username, password, registerDate)
    }

    lateinit var db: SQLiteDatabase


    fun <S : User?> save(user: S): S {
        val userId = db.insert(TABLE_NAME, null, getContentValues(user!!))
        if (userId == -1L)
            throw SQLiteException()
        user.user_id = userId
        return user;
    }

    fun findAll(): MutableList<User> {
        val projection = arrayOf(ID, NAME, USERNAME, PASSWORD, REGISTER_DATE)
        val cursor =
            db.query(TABLE_NAME, projection, null, null, null, null, REGISTER_DATE + " DESC")
        val users = ArrayList<User>()

        if (cursor == null || !cursor.moveToFirst())
            return users
        do {
            val user = getUser(cursor)
            users.add(user)
        } while (cursor.moveToNext())
        return users
    }

    fun findUserByUsername(username: String): Optional<User> {
        val projection = arrayOf(ID, NAME, USERNAME, PASSWORD, REGISTER_DATE)
        val cursor =
            db.query(
                TABLE_NAME, projection, "$USERNAME = '$username'", null, null, null,
                REGISTER_DATE + " " +
                        "DESC"
            )
        if (cursor == null || !cursor.moveToFirst())
            return Optional.empty()
        val user = getUser(cursor)
        return Optional.of(user)
    }

    fun deleteAll() {
        db.delete(TABLE_NAME, null, null)
    }

    fun deleteById(id: Long?) {
        db.delete(TABLE_NAME, "$ID = $id", null)
    }

    fun deleteByUser(user: User?) {
        if (user != null) {
            deleteById(user.user_id)
        }
    }

    fun findUsersByUsernameContains(text: String): MutableIterable<User> {
        val projection = arrayOf(ID, NAME, USERNAME, PASSWORD, REGISTER_DATE)
        val cursor =
            db.query(TABLE_NAME, projection, "$USERNAME like '%$text%'", null, null, null, null)

        cursor.use {
            val users = ArrayList<User>()

            if (it == null || !it.moveToFirst())
                return users
            do
                users.add(getUser(it))
            while (it.moveToNext())

            return users
        }
    }

    fun count(): Long {
        TODO("Not yet implemented")
    }

    fun exitsById(id: Long?): Boolean {
        TODO("Not yet implemented")
    }

    fun findAllById(ids: MutableIterable<Long>?): MutableIterable<User> {
        TODO("Not yet implemented")
    }

    fun findById(id: Long?): Optional<User> {
        TODO("Not yet implemented")
    }

    fun <S : User?> save(entities: MutableIterable<S>?): MutableIterable<S> {
        TODO("Not yet implemented")
    }

}