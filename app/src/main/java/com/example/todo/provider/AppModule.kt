package com.example.todo.provider

import android.content.Context
import androidx.room.Room
import com.example.todo.model.data.TodoDatabase
import com.example.todo.model.ToDoModule
import com.example.todo.model.TodoModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {
    @Singleton
    @Provides
    fun todoProvider (database: TodoDatabase) : ToDoModule {
        return database.dao()
    }

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): TodoDatabase =
        Room.databaseBuilder(
            context,
            TodoDatabase::class.java,
            "todoDb"
        ).fallbackToDestructiveMigration().build()


}