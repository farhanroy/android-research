package com.kimochi.note.modules.main.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.kimochi.note.db.entity.NoteEntity
import com.kimochi.note.repository.LocalNoteRepository
import org.koin.core.KoinComponent
import org.koin.core.inject

class MainVM : ViewModel(), KoinComponent{
    private val repository by inject<LocalNoteRepository>()

    fun getNotes() : LiveData<List<NoteEntity>> =  repository.getNotes().asLiveData()
}