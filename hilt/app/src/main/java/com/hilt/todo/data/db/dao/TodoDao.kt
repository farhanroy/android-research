package com.hilt.todo.data.db.dao

import androidx.room.*
import com.hilt.todo.data.db.entity.Todo
import kotlinx.coroutines.flow.Flow

/**
 * Data access object to query the database.
 */
@Dao
interface TodoDao {

    @Query("SELECT * FROM todo_table")
    fun getAllTodo(): Flow<List<Todo>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTodo(data: Todo)

    @Delete
    suspend fun deleteTodo(data: Todo)
}