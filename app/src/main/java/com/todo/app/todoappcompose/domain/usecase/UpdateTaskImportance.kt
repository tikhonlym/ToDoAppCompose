package com.todo.app.todoappcompose.domain.usecase

import com.todo.app.todoappcompose.domain.objects.TodoImportance
import com.todo.app.todoappcompose.domain.repository.TodoItemsRepository
import javax.inject.Inject

class UpdateTaskImportance @Inject constructor(
    private val repository: TodoItemsRepository,
) {

    fun execute(id: String, importance: TodoImportance) {
        repository.updateTaskImportance(id, importance)
    }
}