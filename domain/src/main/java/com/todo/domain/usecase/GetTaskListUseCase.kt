package com.todo.domain.usecase

import com.todo.domain.NetworkStorage
import com.todo.domain.TaskDao
import com.todo.domain.TaskRepository
import com.todo.domain.model.Task
import com.todo.domain.model.wrapper.ResultWrapper
import com.todo.domain.model.wrapper.UIResultWrapper
import javax.inject.Inject

/** He is responsible for getting the List<Task>, if it fails, he will return a local copy.
 *  Error mapping to ui layer.
 */

class GetTaskListUseCase @Inject constructor(
    private val repository: TaskRepository,
    private val local: TaskDao,
    private val networkStorage: NetworkStorage,
) {

    suspend fun execute(): UIResultWrapper<List<Task>> {
        val result = repository.getTaskList()
        when (result) {
            is ResultWrapper.Success -> {
                local.upsertTasks(result.value.list)
                networkStorage.saveSyncNeeded(result.value.revision != networkStorage.getRevision())
                return UIResultWrapper.Success(local.getTaskList())
            }

            is ResultWrapper.NetworkError -> {
                return UIResultWrapper.SuccessNoInternet(local.getTaskList())
            }

            else -> {
                //TODO: можно отправлять в аналитику другие ошибки
                return UIResultWrapper.SuccessWithServerError(local.getTaskList())
            }
        }
    }
}