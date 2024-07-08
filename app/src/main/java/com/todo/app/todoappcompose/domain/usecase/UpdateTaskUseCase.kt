package com.todo.app.todoappcompose.domain.usecase

import android.util.Log
import com.todo.app.todoappcompose.data.AppUserStorage
import com.todo.app.todoappcompose.data.repository.TodoRepositoryImpl
import com.todo.app.todoappcompose.data.room.TaskDao
import com.todo.app.todoappcompose.domain.wrapper.ResultWrapper
import com.todo.app.todoappcompose.domain.wrapper.UIResultWrapper
import com.todo.app.todoappcompose.domain.model.Task
import javax.inject.Inject

/** He is responsible for updating the Task on server,
 *  if it fails, he will return a local copy of Task.
 *  Error mapping to ui layer.
 */

class UpdateTaskUseCase @Inject constructor(
    private val repository: TodoRepositoryImpl,
    private val appUserStorage: AppUserStorage,
    private val dao: TaskDao,
) {

    suspend fun execute(task: Task): UIResultWrapper<Task> {
        val result = repository.updateTask(task, appUserStorage.getRevision())
        when (result) {
            is ResultWrapper.Success -> {
                appUserStorage.saveRevision(result.value.revision)
                dao.upsertTask(result.value.element)
                return UIResultWrapper.Success(result.value.element)
            }

            is ResultWrapper.NetworkError -> {
                dao.upsertTask(task)
                appUserStorage.saveSyncNeeded(true)
                return UIResultWrapper.SuccessNoInternet(task)
            }

            is ResultWrapper.UnknownSeverError -> {
                dao.upsertTask(task)
                appUserStorage.saveSyncNeeded(true)
                return UIResultWrapper.SuccessSynchronizationNeeded(task)
            }

            else -> {
                Log.w("NetworkException", "result: $result")
                dao.upsertTask(task)
                appUserStorage.saveSyncNeeded(true)
                return UIResultWrapper.SuccessWithServerError(task)
            }
        }
    }
}