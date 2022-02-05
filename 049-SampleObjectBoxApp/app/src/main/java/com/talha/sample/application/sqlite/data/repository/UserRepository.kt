package com.talha.sample.application.sqlite.data.repository

import com.talha.sample.application.sqlite.data.entity.User
import com.talha.sample.application.sqlite.data.entity.User_
import com.talha.sample.application.sqlite.data.objectbox.ObjectBox
import java.util.*

object UserRepository {


    fun <S : User?> save(user: S): S {
        user!!.id = ObjectBox.userBox.put(user)
        return user;
    }

    fun findAll(): MutableList<User> {
        return ObjectBox.userBox.query().build().find()
    }

    fun findUserByUsername(username: String): Optional<User> {
        return Optional.ofNullable(ObjectBox.userBox.query().equal(User_.username, username).build().findFirst())
    }

    fun deleteAll(): Int {
        return ObjectBox.userBox.panicModeRemoveAll().toInt()
    }

    fun deleteById(id: Long?) {
        ObjectBox.userBox.remove(id!!)
    }

    fun deleteByUser(user: User?) {
        ObjectBox.userBox.remove(user!!)
    }

    fun findUsersByUsernameContains(text: String): MutableIterable<User> {
        return ObjectBox.userBox.query().contains(User_.username, text).build().find()
    }

    fun count(): Long {
        return ObjectBox.userBox.count()
    }

    fun exitsById(id: Long?): Boolean {
        TODO("Not yet implemented")
    }

    fun findAllById(ids: MutableIterable<Long>?): MutableIterable<User> {
        TODO("Not yet implemented")
    }

    fun findById(id: Long?): Optional<User> {
        var user = ObjectBox.userBox.query().filter { it.id == id }.build().findFirst()
        return Optional.of(user!!)
    }

    fun <S : User?> save(entities: MutableIterable<S>?): MutableIterable<S> {
        TODO("Not yet implemented")
    }

}