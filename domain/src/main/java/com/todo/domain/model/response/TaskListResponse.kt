package com.todo.domain.model.response

import com.todo.domain.model.Task

data class TaskListResponse(
    val status: String,
    val list: List<Task>,
    val revision: Int,
)