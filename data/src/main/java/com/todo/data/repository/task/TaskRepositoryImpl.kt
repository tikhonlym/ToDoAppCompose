package com.todo.data.repository.task

import com.todo.core.dispatchers.AppDispatchers
import com.todo.data.repository.BaseRepository
import com.todo.domain.TaskRepository
import com.todo.domain.model.Task
import com.todo.domain.model.response.TaskListResponse
import com.todo.domain.model.response.TaskResponse
import com.todo.domain.model.wrapper.ResultWrapper

/** Repository for getting data from the network */

class TaskRepositoryImpl(
    private val dispatcher: AppDispatchers,
    private val api: TaskApiCalls,
) : TaskRepository {

    override suspend fun getTaskList(): ResultWrapper<TaskListResponse> {
        return BaseRepository.safeAPICall(dispatcher.io) {
            api.getList().await()
        }
    }

    override suspend fun getTask(id: String): ResultWrapper<TaskResponse> {
        return BaseRepository.safeAPICall(dispatcher.io) {
            api.getTask(id = id).await()
        }
    }

    override suspend fun deleteTask(id: String, revision: Int): ResultWrapper<TaskResponse> {
        return BaseRepository.safeAPICall(dispatcher.io) {
            api.deleteTask(revision = revision, id = id).await()
        }
    }

    override suspend fun createTask(task: Task, revision: Int): ResultWrapper<TaskResponse> {
        return BaseRepository.safeAPICall(dispatcher.io) {
            api.createTask(
                revision = revision,
                data = UpdateTaskRequest(element = task)
            ).await()
        }
    }

    override suspend fun updateTaskList(
        list: List<Task>,
        revision: Int,
    ): ResultWrapper<TaskListResponse> {
        return BaseRepository.safeAPICall(dispatcher.io) {
            api.updateList(
                revision = revision,
                data = UpdateListRequest(list = list)
            ).await()
        }
    }

    override suspend fun updateTask(task: Task, revision: Int): ResultWrapper<TaskResponse> {
        return BaseRepository.safeAPICall(dispatcher.io) {
            api.updateTask(
                revision = revision,
                id = task.id,
                task = UpdateTaskRequest(element = task)
            ).await()
        }
    }
}