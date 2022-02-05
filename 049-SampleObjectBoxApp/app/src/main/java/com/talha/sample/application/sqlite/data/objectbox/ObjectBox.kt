package com.talha.sample.application.sqlite.data.objectbox

import android.content.Context
import com.talha.sample.application.sqlite.data.entity.MyObjectBox
import com.talha.sample.application.sqlite.data.entity.User
import io.objectbox.Box
import io.objectbox.BoxStore

object ObjectBox {
    lateinit var boxStore: BoxStore
        private set

    lateinit var userBox: Box<User>
        private set

    fun initialize(context: Context) {
        boxStore = MyObjectBox.builder()
            .androidContext(context.applicationContext)
            .build()
        userBox = boxStore.boxFor(User::class.java)
    }
}