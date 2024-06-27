package com.todo.app.todoappcompose.domain.usecase

import com.todo.app.todoappcompose.domain.objects.TodoItem
import com.todo.app.todoappcompose.domain.repository.TodoItemsRepository
import javax.inject.Inject

class CreateTask @Inject constructor(
    private val repository: TodoItemsRepository,
) {

    fun execute(task: TodoItem) {
        repository.createTask(task)
    }
}