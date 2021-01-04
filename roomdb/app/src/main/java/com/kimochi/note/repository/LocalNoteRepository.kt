package com.kimochi.note.repository

import com.kimochi.note.db.dao.NoteDao
import com.kimochi.note.db.entity.NoteEntity
import kotlinx.coroutines.flow.Flow

class LocalNoteRepository(private val dao : NoteDao){
    suspend fun insertNote(data : NoteEntity){
        dao.insertNote(data)
    }
    fun getNotes(): Flow<List<NoteEntity>> = dao.getNotes()

    suspend fun deleteNote(note: NoteEntity) = dao.deleteNote(note)

    suspend fun getNoteById(id : Int):NoteEntity = dao.getNoteById(id)

    suspend fun updateNote(note: NoteEntity) = dao.updateNote(note)
}