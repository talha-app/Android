package com.talha.todoinfoapplication.repository

import android.content.Context
import com.talha.todoinfoapplication.entity.UserInfo
import dagger.hilt.android.qualifiers.ApplicationContext
import org.csystem.util.data.repository.ICrudRepository
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList


class UserInfoRepository @Inject constructor(@ApplicationContext val mContext: Context) :
    ICrudRepository<UserInfo, Long> {

    companion object {
        private val mUsers = ArrayList<UserInfo>()
        private var index = 0L
    }

    override fun count(): Long {
        return mUsers.size.toLong()
    }

    override fun <S : UserInfo?> save(entity: S): S {
        entity!!.id = ++index
        mUsers.add(entity)
        return entity
    }

    override fun findAll(): MutableIterable<UserInfo> {
        return mUsers
    }

    fun findByUsername(username: String): Optional<UserInfo> {
        return Optional.ofNullable(mUsers.filter { it.username == username }.firstOrNull())
    }

    override fun <S : UserInfo?> save(entity: MutableIterable<S>?): MutableIterable<S> {
        TODO("Not yet implemented")
    }


    override fun delete(entity: UserInfo?) {
        TODO("Not yet implemented")
    }

    override fun deleteAll() {
        TODO("Not yet implemented")
    }

    override fun deleteAll(entities: MutableIterable<UserInfo>?) {
        TODO("Not yet implemented")
    }

    override fun deleteById(id: Long?) {
        TODO("Not yet implemented")
    }

    override fun exitsById(id: Long?): Boolean {
        TODO("Not yet implemented")
    }


    override fun findAllById(ids: MutableIterable<Long>?): MutableIterable<UserInfo> {
        TODO("Not yet implemented")
    }

    override fun findById(id: Long?): Optional<UserInfo> {
        TODO("Not yet implemented")
    }


}