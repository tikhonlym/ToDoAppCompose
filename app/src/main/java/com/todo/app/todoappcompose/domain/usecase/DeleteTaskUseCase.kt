package com.todo.app.todoappcompose.domain.usecase

import com.todo.app.todoappcompose.data.repository.todo.TodoItemsRepositoryImpl
import javax.inject.Inject

class DeleteTaskUseCase @Inject constructor(
    private val repository: TodoItemsRepositoryImpl,
) {

    suspend fun execute(id: String) {
        repository.deleteTask(id)
    }
}