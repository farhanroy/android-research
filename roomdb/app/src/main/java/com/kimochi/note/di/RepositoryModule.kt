package com.kimochi.note.di

import com.kimochi.note.db.dao.NoteDao
import com.kimochi.note.repository.LocalNoteRepository
import org.koin.dsl.module

val repositoryModule = module {
    factory{ LocalNoteRepository(get() as NoteDao) }
}