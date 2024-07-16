package com.todo.domain.model.response

import com.todo.domain.model.Task

data class TaskResponse(
    val status: String,
    val element: Task,
    val revision: Int,
)