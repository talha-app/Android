package com.talha.todoinfo.application.dal

import com.talha.todoinfoapplication.entity.TodoInfo
import com.talha.todoinfoapplication.entity.UserInfo
import com.talha.todoinfoapplication.repository.TodoInfoRepository
import com.talha.todoinfoapplication.repository.UserInfoRepository
import java.time.LocalDateTime
import java.util.*
import javax.inject.Inject

class TodoInfoApplicationDBHelper @Inject constructor(
    private val mUserInfoRepository: UserInfoRepository,
    private val mTodoInfoRepository: TodoInfoRepository
) {
    fun findAllTodos(): Iterable<TodoInfo> {
        return mTodoInfoRepository.findAll()
    }

    fun findAllUsers(): Iterable<UserInfo> {
        return mUserInfoRepository.findAll()
    }

    fun findUserByUsername(username: String): Optional<UserInfo> {
        return mUserInfoRepository.findByUsername(username)
    }

    fun findTodoByStartDate(startDate: LocalDateTime): Iterable<TodoInfo> {
        return mTodoInfoRepository.findByStartDate(startDate)
    }

    fun saveUserInfo(userInfo: UserInfo?): UserInfo? {
        return mUserInfoRepository.save(userInfo)
    }

    fun saveTodoInfo(todoInfo: TodoInfo): TodoInfo? {
        return mTodoInfoRepository.save(todoInfo)
    }
}