package com.todo.domain.usecase

import com.todo.domain.NetworkStorage
import com.todo.domain.TaskDao
import com.todo.domain.TaskRepository
import com.todo.domain.model.Task
import com.todo.domain.model.wrapper.ResultWrapper
import com.todo.domain.model.wrapper.UIResultWrapper
import javax.inject.Inject

/** He is responsible for deleting the Task, if it fails, he will delete a local copy.
 *  Error mapping to ui layer.
 */

class DeleteTaskUseCase @Inject constructor(
    private val repository: TaskRepository,
    private val local: TaskDao,
    private val networkStorage: NetworkStorage,
) {

    suspend fun execute(id: String): UIResultWrapper<Task> {
        val result = repository.deleteTask(id, networkStorage.getRevision())
        val uiResult = when (result) {
            is ResultWrapper.Success -> {
                networkStorage.saveRevision(result.value.revision)
                networkStorage.saveSyncNeeded(false)
                UIResultWrapper.Success(result.value.element)
            }

            is ResultWrapper.NetworkError -> {
                networkStorage.saveSyncNeeded(true)
                UIResultWrapper.SuccessNoInternet(local.getTask(id))
            }

            is ResultWrapper.UnSynchronizedDataError -> {
                networkStorage.saveSyncNeeded(true)
                UIResultWrapper.SuccessSynchronizationNeeded(local.getTask(id))
            }

            else -> {
                //TODO: можно отправлять в аналитику другие ошибки
                networkStorage.saveSyncNeeded(true)
                UIResultWrapper.SuccessWithServerError(local.getTask(id))
            }
        }
        local.deleteTaskById(id)
        return uiResult
    }
}