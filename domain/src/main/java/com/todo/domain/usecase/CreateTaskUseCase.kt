package com.todo.domain.usecase

import com.todo.domain.NetworkStorage
import com.todo.domain.TaskDao
import com.todo.domain.TaskRepository
import com.todo.domain.model.Task
import com.todo.domain.model.wrapper.ResultWrapper
import com.todo.domain.model.wrapper.UIResultWrapper
import javax.inject.Inject

/** He is responsible for creating the Task, if it fails, he will make a local copy.
 *  Error mapping to ui layer.
 */

class CreateTaskUseCase @Inject constructor(
    private val repository: TaskRepository,
    private val local: TaskDao,
    private val networkStorage: NetworkStorage,
) {

    suspend fun execute(task: Task): UIResultWrapper<Task> {
        val result = repository.createTask(task, networkStorage.getRevision())
        when (result) {
            is ResultWrapper.Success -> {
                networkStorage.saveRevision(result.value.revision)
                local.upsertTask(result.value.element)
                networkStorage.saveSyncNeeded(false)
                return UIResultWrapper.Success(result.value.element)
            }

            is ResultWrapper.NetworkError -> {
                local.upsertTask(task)
                networkStorage.saveSyncNeeded(true)
                return UIResultWrapper.SuccessNoInternet(task)
            }

            is ResultWrapper.UnSynchronizedDataError -> {
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