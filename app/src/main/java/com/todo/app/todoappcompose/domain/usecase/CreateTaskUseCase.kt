package com.todo.app.todoappcompose.domain.usecase

import android.util.Log
import com.todo.app.todoappcompose.data.AppUserStorage
import com.todo.app.todoappcompose.data.repository.TodoRepositoryImpl
import com.todo.app.todoappcompose.data.room.TaskDao
import com.todo.app.todoappcompose.domain.wrapper.ResultWrapper
import com.todo.app.todoappcompose.domain.wrapper.UIResultWrapper
import com.todo.app.todoappcompose.domain.model.Task
import javax.inject.Inject

/** He is responsible for creating the Task, if it fails, he will make a local copy.
 *  Error mapping to ui layer.
 */

class CreateTaskUseCase @Inject constructor(
    private val repository: TodoRepositoryImpl,
    private val appUserStorage: AppUserStorage,
    private val dao: TaskDao,
) {

    suspend fun execute(task: Task): UIResultWrapper<Task> {
        val result = repository.createTask(task, appUserStorage.getRevision())
        when (result) {
            is ResultWrapper.Success -> {
                appUserStorage.saveRevision(result.value.revision)
                dao.upsertTask(result.value.element)
                appUserStorage.saveSyncNeeded(false)
                return UIResultWrapper.Success(result.value.element)
            }

            is ResultWrapper.NetworkError -> {
                dao.upsertTask(task)
                appUserStorage.saveSyncNeeded(true)
                return UIResultWrapper.SuccessNoInternet(task)
            }

            is ResultWrapper.UnSynchronizedDataError -> {
                dao.upsertTask(task)
                appUserStorage.saveSyncNeeded(true)
                return UIResultWrapper.SuccessSynchronizationNeeded(task)
            }

            else -> {
                dao.upsertTask(task)
                appUserStorage.saveSyncNeeded(true)
                Log.w("NetworkException", "result: $result")
                return UIResultWrapper.SuccessWithServerError(task)
            }
        }
    }
}