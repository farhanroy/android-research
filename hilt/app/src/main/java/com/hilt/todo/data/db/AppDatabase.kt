package com.hilt.todo.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.hilt.todo.data.db.dao.TodoDao
import com.hilt.todo.data.db.entity.Todo
import com.hilt.todo.utils.Constants

@Database(entities = [Todo::class], version = Constants.DB_VERSION, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun todoDao(): TodoDao
}