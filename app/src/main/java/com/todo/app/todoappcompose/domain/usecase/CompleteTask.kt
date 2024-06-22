package com.todo.app.todoappcompose.domain.usecase

import com.todo.app.todoappcompose.domain.repository.TodoItemsRepository
import javax.inject.Inject

class CompleteTask @Inject constructor(
    private val repository: TodoItemsRepository,
) {

    fun execute(id: String, isDone: Boolean) {
        repository.completeTask(id, isDone)
    }
}