package com.todo.app.todoappcompose.domain.repository

import com.todo.app.todoappcompose.domain.objects.TodoItem

interface TodoItemsRepository {
    suspend fun getTaskList(): List<TodoItem>
    suspend fun completeTask(id: String, isDone: Boolean)
    suspend fun countCompletedTask(): Int
    suspend fun getTask(id: String): TodoItem
    suspend fun deleteTask(id: String)
    suspend fun createOrUpdateTask(task: TodoItem)
}