package com.talha.todoinfo.application.data.service

import com.talha.todoinfo.application.dal.TodoInfoApplicationDBHelper
import com.talha.todoinfoapplication.entity.TodoInfo
import javax.inject.Inject
import org.csystem.util.data.DatabaseUtil as DB

class TodoApplicationDataService @Inject constructor(private val mtodoInfoApplicationDataHelper: TodoInfoApplicationDBHelper) {

    fun findAllTodos(): Iterable<TodoInfo> {
        return DB.doWorkForService("TodoApplicationDataService - findAllTodos") { mtodoInfoApplicationDataHelper.findAllTodos() }
    }


    fun saveTodo(todoInfo: TodoInfo): TodoInfo {
        return DB.doWorkForService("TodoApplicationDataService.findAllTodos")
        {
            mtodoInfoApplicationDataHelper.saveTodoInfo(todoInfo)!!
        }
    }
}