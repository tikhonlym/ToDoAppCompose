package com.todo.app.todoappcompose.domain.objects


data class TodoItem(
    val id: String,
    val text: String,
    val importance: TodoImportance = TodoImportance.NORMAL,
    val deadline: TaskDate? = null,
    val isDone: Boolean = false,
    val creationDate: TaskDate,
)