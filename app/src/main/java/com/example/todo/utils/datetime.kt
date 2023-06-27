package com.example.todo.utils

import java.util.Date
import java.text.SimpleDateFormat

fun convertTimestampToTime(timestamp: Long): String {
    val sdf = SimpleDateFormat("hh:mm a") // Define the desired time format
    val date = Date(timestamp) // Create a Date object from the timestamp
    return sdf.format(date) // Format the date as per the desired format
}
