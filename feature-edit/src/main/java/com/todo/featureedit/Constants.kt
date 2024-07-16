package com.todo.featureedit

import com.todo.domain.model.Task
import com.todo.domain.model.TaskImportance

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