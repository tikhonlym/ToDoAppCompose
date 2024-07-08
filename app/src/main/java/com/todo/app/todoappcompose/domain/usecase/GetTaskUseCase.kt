package com.todo.app.todoappcompose.domain.usecase

import android.util.Log
import com.todo.app.todoappcompose.data.repository.TodoRepositoryImpl
import com.todo.app.todoappcompose.data.room.TaskDao
import com.todo.app.todoappcompose.domain.wrapper.ResultWrapper
import com.todo.app.todoappcompose.domain.wrapper.UIResultWrapper
import com.todo.app.todoappcompose.domain.model.Task
import javax.inject.Inject

/** He is responsible for getting the Task if it fails, he will return a local copy.
 *  Error mapping to ui layer.
 */

class GetTaskUseCase @Inject constructor(
    private val repository: TodoRepositoryImpl,
    private val dao: TaskDao,
) {

    suspend fun execute(id: String): UIResultWrapper<Task> {
        val result = repository.getTask(id)
        when (result) {
            is ResultWrapper.Success -> {
                dao.upsertTask(result.value.element)
                return UIResultWrapper.Success(dao.getTask(id))
            }

            ResultWrapper.NetworkError -> {
                return UIResultWrapper.SuccessNoInternet(dao.getTask(id))
            }

            else -> {
                Log.w("NetworkException", "result: $result")
                return UIResultWrapper.SuccessWithServerError(dao.getTask(id))
            }
        }
    }
}