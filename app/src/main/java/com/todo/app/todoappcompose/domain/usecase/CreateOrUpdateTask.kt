package com.todo.app.todoappcompose.domain.usecase

import com.todo.app.todoappcompose.domain.objects.TodoItem
import com.todo.app.todoappcompose.domain.repository.TodoItemsRepository
import javax.inject.Inject

class CreateOrUpdateTask @Inject constructor(
    private val repository: TodoItemsRepository,
) {

    suspend fun execute(task: TodoItem) {
        repository.createOrUpdateTask(task)
    }
}