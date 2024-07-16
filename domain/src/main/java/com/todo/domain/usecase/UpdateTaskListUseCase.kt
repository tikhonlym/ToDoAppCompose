package com.todo.domain.usecase

import com.todo.domain.NetworkStorage
import com.todo.domain.TaskDao
import com.todo.domain.TaskRepository
import com.todo.domain.model.Task
import com.todo.domain.model.wrapper.ResultWrapper
import com.todo.domain.model.wrapper.UIResultWrapper
import javax.inject.Inject

/** He is responsible for updating the List<Task> on server by patch request,
 *  if it fails, he will return a local copy.
 *  Error mapping to ui layer.
 */

class UpdateTaskListUseCase @Inject constructor(
    private val repository: TaskRepository,
    private val local: TaskDao,
    private val networkStorage: NetworkStorage,
) {

    suspend fun execute(): UIResultWrapper<List<Task>> {
        val result = repository.updateTaskList(local.getTaskList(), networkStorage.getRevision())
        when (result) {
            is ResultWrapper.Success -> {
                networkStorage.saveRevision(result.value.revision)
                networkStorage.saveSyncNeeded(false)
                local.replaceTable(result.value.list)
                return UIResultWrapper.Success(result.value.list)
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