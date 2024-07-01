package com.todo.app.todoappcompose.presentation.util

import com.todo.app.todoappcompose.domain.objects.TodoImportance
import com.todo.app.todoappcompose.domain.objects.TodoItem

object Constants {

    val emptyTask = TodoItem(
        id = "",
        text = "",
        importance = TodoImportance.NORMAL,
        deadline = null,
        isDone = false,
        creationDate = millisecondsToLocalDate(0L)
    )

    const val DEFAULT_DATE_PATTERN = "dd MMMM yyyy"

}