package com.todo.data.repository.task

import com.todo.domain.model.Task

data class UpdateTaskRequest(
    val element: Task,
)