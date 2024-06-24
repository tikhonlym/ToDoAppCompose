package com.todo.app.todoappcompose.domain.usecase

import com.todo.app.todoappcompose.domain.repository.TodoItemsRepository
import javax.inject.Inject

class GetTask @Inject constructor(
    private val repository: TodoItemsRepository,
) {

    fun execute(id: String) = repository.getTask(id)
}