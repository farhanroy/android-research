package com.kimochi.note.di

import androidx.room.Room
import com.kimochi.note.db.AppDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val databaseModule = module {
    single {
        Room.databaseBuilder(androidContext(), AppDatabase::class.java,AppDatabase.DB_NAME).build()
    }

    single { get<AppDatabase>().getNoteDao() }
}