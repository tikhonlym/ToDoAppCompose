package com.todo.domain.usecase

import com.todo.domain.TaskDao
import com.todo.domain.TaskRepository
import com.todo.domain.model.Task
import com.todo.domain.model.wrapper.ResultWrapper
import com.todo.domain.model.wrapper.UIResultWrapper
import javax.inject.Inject

/** He is responsible for getting the Task if it fails, he will return a local copy.
 *  Error mapping to ui layer.
 */

class GetTaskUseCase @Inject constructor(
    private val repository: TaskRepository,
    private val local: TaskDao,
) {

    suspend fun execute(id: String): UIResultWrapper<Task> {
        val result = repository.getTask(id)
        when (result) {
            is ResultWrapper.Success -> {
                local.upsertTask(result.value.element)
                return UIResultWrapper.Success(local.getTask(id))
            }

            ResultWrapper.NetworkError -> {
                return UIResultWrapper.SuccessNoInternet(local.getTask(id))
            }

            else -> {
                //TODO: можно отправлять в аналитику другие ошибки
                return UIResultWrapper.SuccessWithServerError(local.getTask(id))
            }
        }
    }
}