package com.hilt.todo.data.source

import com.hilt.todo.data.db.dao.TodoDao
import com.hilt.todo.data.db.entity.Todo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TodoLocalSource @Inject constructor(private val dao: TodoDao) {

    suspend fun insertTodo(data : Todo) = dao.insertTodo(data)

    fun getAllTodo(): Flow<List<Todo>> = dao.getAllTodo()

    suspend fun deleteTodo(data: Todo) = dao.deleteTodo(data)
}