package com.example.todo.model.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.example.todo.model.ToDoModule
import com.example.todo.model.TodoModel
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Database(entities = [TodoModel::class], version = 2)
@TypeConverters(TodoDatabase::class)
abstract class TodoDatabase : RoomDatabase() {
    abstract fun dao() : ToDoModule


    companion object {
        private val formatter: DateTimeFormatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME

        @TypeConverter
        @JvmStatic
        fun fromTimestamp(value: Long?): LocalDateTime? {
            return value?.let { LocalDateTime.ofEpochSecond(it, 0, java.time.ZoneOffset.UTC) }
        }

        @TypeConverter
        @JvmStatic
        fun dateToTimestamp(date: LocalDateTime?): Long? {
            return date?.toEpochSecond(java.time.ZoneOffset.UTC)
        }

        @TypeConverter
        @JvmStatic
        fun fromString(value: String?): LocalDateTime? {
            return value?.let { LocalDateTime.parse(it, formatter) }
        }

        @TypeConverter
        @JvmStatic
        fun toString(date: LocalDateTime?): String? {
            return date?.format(formatter)
        }
    }
//
}