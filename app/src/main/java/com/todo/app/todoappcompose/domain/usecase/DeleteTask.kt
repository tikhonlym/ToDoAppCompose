package com.todo.app.todoappcompose.domain.usecase

import com.todo.app.todoappcompose.domain.repository.TodoItemsRepository
import javax.inject.Inject

class DeleteTask @Inject constructor(
    private val repository: TodoItemsRepository,
) {

    suspend fun execute(id: String) {
        repository.deleteTask(id)
    }
}