package com.todo.app.todoappcompose.domain.repository

import com.todo.app.todoappcompose.data.objects.TaskDate
import com.todo.app.todoappcompose.data.objects.TodoImportance
import com.todo.app.todoappcompose.data.objects.TodoItem

interface TodoItemsRepository {
    fun getTaskList(): List<TodoItem>
    fun completeTask(id: String, isDone: Boolean)
    fun countCompletedTask(): Int
    fun getTask(id: String): TodoItem
    fun deleteTask(id: String)
    fun updateTaskDeadline(id: String, date: TaskDate)
    fun updateTaskText(id: String, text: String)
    fun updateTaskImportance(id: String, importance: TodoImportance)
    fun createTask(task: TodoItem)
}