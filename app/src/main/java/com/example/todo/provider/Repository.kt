package com.example.todo.provider


import com.example.todo.model.ToDoModule
import com.example.todo.model.TodoModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class Repository @Inject constructor (private val toDoModule: ToDoModule){

    suspend fun insertTodo(todoModel: TodoModel) = toDoModule.addTodo(todoModel)
    suspend fun deleteTodo(todoModel: TodoModel) = toDoModule.deleteTodo(todoModel)
    suspend fun updateTodo(todoModel: TodoModel) = toDoModule.updateTodo(todoModel)
    suspend fun getTodo() : Flow<List<TodoModel>> = toDoModule.getAll()

}