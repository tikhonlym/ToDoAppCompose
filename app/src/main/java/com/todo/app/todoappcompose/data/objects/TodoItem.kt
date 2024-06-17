package com.todo.app.todoappcompose.data.objects

import java.util.Date

data class TodoItem(
    val id: String,
    val text: String,
    val importance: TodoImportance,
    val deadline: String? = null,
    val isDone: Boolean,
    val creationDate: Date,
    val modificationDate: Date? = null
)