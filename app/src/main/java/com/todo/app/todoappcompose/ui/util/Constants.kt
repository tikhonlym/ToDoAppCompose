package com.todo.app.todoappcompose.ui.util

import com.todo.app.todoappcompose.domain.model.TaskImportance
import com.todo.app.todoappcompose.domain.model.Task

object Constants {

    val emptyTask = Task(
        id = "",
        text = "",
        importance = TaskImportance.basic,
        deadline = null,
        done = false,
        created_at = 0L,
        changed_at = 0L,
        last_updated_by = ""
    )

}