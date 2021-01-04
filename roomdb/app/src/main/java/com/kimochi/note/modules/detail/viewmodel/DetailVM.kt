package com.kimochi.note.modules.detail.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kimochi.note.db.entity.NoteEntity
import com.kimochi.note.repository.LocalNoteRepository
import com.kimochi.note.utils.debug
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.core.KoinComponent
import org.koin.core.inject

class DetailVM : ViewModel(), KoinComponent {
    var title = ""
    var desc = ""

    private val repository by inject<LocalNoteRepository>()

    fun getNoteById(id: Int) = viewModelScope.launch(Dispatchers.IO) {
        try {
            bindView(repository.getNoteById(id))
        } catch (e: Exception) {
            e.message?.let { debug(it) }
        }
    }

    fun deleteById(note: NoteEntity) = viewModelScope.launch(Dispatchers.IO) {
        try {
            repository.deleteNote(note)
            debug("success delete it")
        } catch (e: Exception) {
            e.message?.let { debug(it) }
        }
    }

    private fun bindView(note: NoteEntity) {
        title = note.title
        desc = note.description
    }
}