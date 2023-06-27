package com.example.todo.provider

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todo.model.TodoModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class Viewmodel @Inject constructor(private val repository: Repository): ViewModel() {


    private val _notelist = MutableStateFlow<List<TodoModel>>(emptyList())
    val notelist = _notelist.asStateFlow()


    init {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getTodo().distinctUntilChanged().collect(){ listofNotes->
                if(listofNotes.isNullOrEmpty()){

                }

                else{
                    _notelist.value=listofNotes
                }
            }
        }
    }

    suspend fun addNote(todo : TodoModel)=viewModelScope.launch { repository.insertTodo(todo) }
    suspend fun updateNote(todo : TodoModel)=viewModelScope.launch { repository.updateTodo(todo) }
    suspend fun removeNote(todo : TodoModel)=viewModelScope.launch { repository.deleteTodo(todo) }

}