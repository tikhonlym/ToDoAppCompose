package com.todo.app.todoappcompose.domain.usecase

import com.todo.app.todoappcompose.data.repository.todo.TodoItemsRepositoryImpl
import com.todo.app.todoappcompose.domain.objects.TodoItem
import javax.inject.Inject

class CreateOrUpdateTaskUseCase @Inject constructor(
    private val repository: TodoItemsRepositoryImpl,
) {

    suspend fun execute(task: TodoItem) {
        repository.createOrUpdateTask(task)
    }
}