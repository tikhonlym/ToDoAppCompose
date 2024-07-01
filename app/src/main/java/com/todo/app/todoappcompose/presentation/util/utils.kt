package com.todo.app.todoappcompose.presentation.util

import java.util.Calendar
import java.util.Date

fun generateUniqueIdForTask(): String {
    val currentDate = getCurrentDateTime().time
    return currentDate.toByte().toString()
}

fun getCurrentDateTime(): Date {
    return Calendar.getInstance().time
}
