package com.example.todo.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime


@Entity(tableName = "todo")
data class TodoModel(
    @PrimaryKey(autoGenerate = true)
    val id  : Int? = null,
    @ColumnInfo
    val done : Boolean = false,
    @ColumnInfo(name = "title")
    val title : String ,
    @ColumnInfo("description")
    val description : String,
    @ColumnInfo("datetime")
    val date : LocalDateTime,
    @ColumnInfo("datetimenow")
    val created : LocalDateTime = LocalDateTime.now()
)