package com.todo.app.todoappcompose.domain.usecase

import android.util.Log
import com.todo.app.todoappcompose.data.AppUserStorage
import com.todo.app.todoappcompose.data.repository.TodoRepositoryImpl
import com.todo.app.todoappcompose.data.room.TaskDao
import com.todo.app.todoappcompose.domain.model.Task
import com.todo.app.todoappcompose.domain.wrapper.ResultWrapper
import com.todo.app.todoappcompose.domain.wrapper.UIResultWrapper
import javax.inject.Inject

/** He is responsible for deleting the Task, if it fails, he will delete a local copy.
 *  Error mapping to ui layer.
 */

class DeleteTaskUseCase @Inject constructor(
    private val repository: TodoRepositoryImpl,
    private val appUserStorage: AppUserStorage,
    private val dao: TaskDao,
) {

    suspend fun execute(id: String): UIResultWrapper<Task> {
        val result = repository.deleteTask(id, appUserStorage.getRevision())
        val uiResult = when (result) {
            is ResultWrapper.Success -> {
                appUserStorage.saveRevision(result.value.revision)
                appUserStorage.saveSyncNeeded(false)
                UIResultWrapper.Success(result.value.element)
            }

            is ResultWrapper.NetworkError -> {
                appUserStorage.saveSyncNeeded(true)
                UIResultWrapper.SuccessNoInternet(dao.getTask(id))
            }

            is ResultWrapper.UnSynchronizedDataError -> {
                appUserStorage.saveSyncNeeded(true)
                UIResultWrapper.SuccessSynchronizationNeeded(dao.getTask(id))
            }

            else -> {
                Log.w("NetworkException", "result: $result")
                appUserStorage.saveSyncNeeded(true)
                UIResultWrapper.SuccessWithServerError(dao.getTask(id))
            }
        }
        dao.deleteTaskById(id)
        return uiResult
    }
}