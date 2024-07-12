package com.todo.domain

import com.todo.domain.model.Task
import com.todo.domain.model.response.TaskListResponse
import com.todo.domain.model.response.TaskResponse
import com.todo.domain.model.wrapper.ResultWrapper

interface TaskRepository {
    suspend fun getTaskList(): ResultWrapper<TaskListResponse>
    suspend fun getTask(id: String): ResultWrapper<TaskResponse>
    suspend fun deleteTask(id: String, revision: Int): ResultWrapper<TaskResponse>
    suspend fun createTask(task: Task, revision: Int): ResultWrapper<TaskResponse>
    suspend fun updateTaskList(list: List<Task>, revision: Int): ResultWrapper<TaskListResponse>
    suspend fun updateTask(task: Task, revision: Int): ResultWrapper<TaskResponse>
}