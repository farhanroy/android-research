package com.kimochi.note.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.kimochi.note.db.dao.NoteDao
import com.kimochi.note.db.entity.NoteEntity

@Database(entities = [NoteEntity::class], version = AppDatabase.DB_VERSION, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getNoteDao() : NoteDao

    companion object {
        const val DB_VERSION = 1
        const val DB_NAME = "note.db"
    }
}