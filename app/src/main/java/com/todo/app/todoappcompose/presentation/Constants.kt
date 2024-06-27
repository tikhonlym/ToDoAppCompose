package com.todo.app.todoappcompose.presentation

import com.todo.app.todoappcompose.domain.objects.TaskDate
import com.todo.app.todoappcompose.domain.objects.TodoImportance
import com.todo.app.todoappcompose.domain.objects.TodoItem

object Constants {

    val emptyTask = TodoItem(
        id = "",
        text = "",
        importance = TodoImportance.NORMAL,
        deadline = null,
        isDone = false,
        creationDate = TaskDate(0L)
    )

}