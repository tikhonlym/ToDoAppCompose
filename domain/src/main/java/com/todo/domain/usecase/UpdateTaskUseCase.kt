package com.todo.domain.usecase

import com.todo.domain.NetworkStorage
import com.todo.domain.TaskDao
import com.todo.domain.TaskRepository
import com.todo.domain.model.Task
import com.todo.domain.model.wrapper.ResultWrapper
import com.todo.domain.model.wrapper.UIResultWrapper
import javax.inject.Inject

/** He is responsible for updating the Task on server,
 *  if it fails, he will return a local copy of Task.
 *  Error mapping to ui layer.
 */

class UpdateTaskUseCase @Inject constructor(
    private val repository: TaskRepository,
    private val local: TaskDao,
    private val networkStorage: NetworkStorage,
) {

    suspend fun execute(task: Task): UIResultWrapper<Task> {
        val result = repository.updateTask(task, networkStorage.getRevision())
        when (result) {
            is ResultWrapper.Success -> {
                networkStorage.saveRevision(result.value.revision)
                local.upsertTask(result.value.element)
                return UIResultWrapper.Success(result.value.element)
            }

            is ResultWrapper.NetworkError -> {
                local.upsertTask(task)
                networkStorage.saveSyncNeeded(true)
                return UIResultWrapper.SuccessNoInternet(task)
            }

            is ResultWrapper.UnknownSeverError -> {
                local.upsertTask(task)
                networkStorage.saveSyncNeeded(true)
                return UIResultWrapper.SuccessSynchronizationNeeded(task)
            }

            else -> {
                //TODO: можно отправлять в аналитику другие ошибки
                local.upsertTask(task)
                networkStorage.saveSyncNeeded(true)
                return UIResultWrapper.SuccessWithServerError(task)
            }
        }
    }
}