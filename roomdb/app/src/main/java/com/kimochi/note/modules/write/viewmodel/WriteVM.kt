package com.kimochi.note.modules.write.viewmodel

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

class WriteVM : ViewModel(), KoinComponent {
    val id = MutableLiveData<Int>()
    val title = MutableLiveData<String>()
    val desc = MutableLiveData<String>()


    private val repository by inject<LocalNoteRepository>()

    fun saveNote() = viewModelScope.launch(Dispatchers.IO) {
        try {
            repository.insertNote(NoteEntity(0, title.value!!, desc.value!!))
            debug("success save it")
        } catch (e: Exception) {
            e.message?.let { debug(it) }
        }
    }

    fun updateNote() = viewModelScope.launch(Dispatchers.IO) {
        try {
            repository.updateNote(NoteEntity(id.value!!, title.value!!, desc.value!!))
            debug("success save it")
        } catch (e: Exception) {
            e.message?.let { debug(it) }
        }
    }

}