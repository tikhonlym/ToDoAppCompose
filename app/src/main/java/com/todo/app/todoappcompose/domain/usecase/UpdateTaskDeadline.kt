package com.todo.app.todoappcompose.domain.usecase

import com.todo.app.todoappcompose.data.objects.TaskDate
import com.todo.app.todoappcompose.domain.repository.TodoItemsRepository
import javax.inject.Inject

class UpdateTaskDeadline @Inject constructor(
    private val repository: TodoItemsRepository,
) {

    fun execute(id: String, date: TaskDate) {
        repository.updateTaskDeadline(id, date)
    }
}