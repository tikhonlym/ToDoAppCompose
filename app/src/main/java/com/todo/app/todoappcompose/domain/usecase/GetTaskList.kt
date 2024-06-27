package com.todo.app.todoappcompose.domain.usecase

import com.todo.app.todoappcompose.domain.objects.TodoItem
import com.todo.app.todoappcompose.domain.repository.TodoItemsRepository
import javax.inject.Inject

class GetTaskList @Inject constructor(
    private val repository: TodoItemsRepository,
) {

    fun execute(): List<TodoItem> {
        return repository.getTaskList()
    }
}