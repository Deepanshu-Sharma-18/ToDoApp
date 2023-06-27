package com.example.todo.model


import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow


@Dao
interface ToDoModule {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addTodo(todoModel: TodoModel)
    @Update
    suspend fun updateTodo(todoModel: TodoModel)
    @Delete
    suspend fun deleteTodo(todoModel: TodoModel)
    @Query("SELECT * FROM todo")
    fun getAll() : Flow<List<TodoModel>>
}