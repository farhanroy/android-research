package com.hilt.todo.view.viewmodel

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hilt.todo.data.db.entity.Todo
import com.hilt.todo.data.source.TodoLocalSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class MainVM  @ViewModelInject constructor(
    private val repository: TodoLocalSource,
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    val inputTodo = MutableLiveData<String>()

    fun getAllTodo(): Flow<List<Todo>> = repository.getAllTodo()

    fun insertTodo() = viewModelScope.launch(Dispatchers.IO) {
        try {
            repository.insertTodo(
                Todo(0, inputTodo.value ?: ""))
        } catch (e: Exception){
            e.printStackTrace()
        }
    }


}