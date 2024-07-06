package com.todo.app.todoappcompose.data.repository

import com.todo.app.todoappcompose.app.dispatchers.AppDispatchers
import com.todo.app.todoappcompose.domain.wrapper.ResultWrapper
import com.todo.app.todoappcompose.domain.model.Task

/** Repository for getting data from the network */

class TodoRepositoryImpl(
    private val dispatcher: AppDispatchers,
    private val api: TodoApiCalls,
) {

    suspend fun getTaskList(): ResultWrapper<TaskListResponse> {
        return BaseRepository.safeAPICall(dispatcher.io) {
            api.getList().await()
        }
    }

    suspend fun getTask(id: String): ResultWrapper<TaskResponse> {
        return BaseRepository.safeAPICall(dispatcher.io) {
            api.getTask(id = id).await()
        }
    }

    suspend fun deleteTask(id: String, revision: Int): ResultWrapper<TaskResponse> {
        return BaseRepository.safeAPICall(dispatcher.io) {
            api.deleteTask(revision = revision, id = id).await()
        }
    }

    suspend fun createTask(task: Task, revision: Int): ResultWrapper<TaskResponse> {
        return BaseRepository.safeAPICall(dispatcher.io) {
            api.createTask(
                revision = revision,
                data = UpdateTaskRequest(element = task)
            ).await()
        }
    }

    suspend fun updateTaskList(list: List<Task>, revision: Int): ResultWrapper<TaskListResponse> {
        return BaseRepository.safeAPICall(dispatcher.io) {
            api.updateList(
                revision = revision,
                data = UpdateListRequest(list = list)
            ).await()
        }
    }

    suspend fun updateTask(task: Task, revision: Int): ResultWrapper<TaskResponse> {
        return BaseRepository.safeAPICall(dispatcher.io) {
            api.updateTask(
                revision = revision,
                id = task.id,
                task = UpdateTaskRequest(element = task)
            ).await()
        }
    }
}