package com.todo.app.todoappcompose.domain.usecase

import android.util.Log
import com.todo.app.todoappcompose.data.AppUserStorage
import com.todo.app.todoappcompose.data.repository.TodoRepositoryImpl
import com.todo.app.todoappcompose.data.room.TaskDao
import com.todo.app.todoappcompose.domain.model.Task
import com.todo.app.todoappcompose.domain.wrapper.ResultWrapper
import com.todo.app.todoappcompose.domain.wrapper.UIResultWrapper
import javax.inject.Inject

/** He is responsible for getting the List<Task>, if it fails, he will return a local copy.
 *  Error mapping to ui layer.
 */

class GetTaskListUseCase @Inject constructor(
    private val repository: TodoRepositoryImpl,
    private val appUserStorage: AppUserStorage,
    private val dao: TaskDao,
) {

    suspend fun execute(): UIResultWrapper<List<Task>> {
        val result = repository.getTaskList()
        when (result) {
            is ResultWrapper.Success -> {
                dao.upsertTasks(result.value.list)
                appUserStorage.saveSyncNeeded(result.value.revision != appUserStorage.getRevision())
                return UIResultWrapper.Success(dao.getTaskList())
            }

            is ResultWrapper.NetworkError -> {
                return UIResultWrapper.SuccessNoInternet(dao.getTaskList())
            }

            else -> {
                Log.w("NetworkException", "result: $result")
                return UIResultWrapper.SuccessWithServerError(dao.getTaskList())
            }
        }
    }
}