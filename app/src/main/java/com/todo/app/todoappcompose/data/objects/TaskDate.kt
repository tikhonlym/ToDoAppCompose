package com.todo.app.todoappcompose.data.objects

import java.text.SimpleDateFormat
import java.util.Date

data class TaskDate(
    val time: Long
) {

    override fun toString(): String {
        val date = Date(time)
        val format = SimpleDateFormat.getDateInstance()
        return format.format(date)
    }
}