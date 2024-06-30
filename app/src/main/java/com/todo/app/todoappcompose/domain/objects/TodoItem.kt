package com.todo.app.todoappcompose.domain.objects

import java.time.LocalDate


data class TodoItem(
    val id: String,
    val text: String,
    val importance: TodoImportance = TodoImportance.NORMAL,
    val deadline: LocalDate? = null,
    val isDone: Boolean = false,
    val creationDate: LocalDate,
)