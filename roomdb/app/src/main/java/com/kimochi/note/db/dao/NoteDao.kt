package com.kimochi.note.db.dao

import androidx.room.*
import com.kimochi.note.db.entity.NoteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(data: NoteEntity)

    @Query("SELECT * FROM note_table")
    fun getNotes(): Flow<List<NoteEntity>>

    @Query("SELECT * from note_table where id = :id LIMIT 1")
    suspend fun getNoteById(id: Int): NoteEntity

    @Update
    suspend fun updateNote(data: NoteEntity)

    @Delete
    suspend fun deleteNote(data: NoteEntity)

}