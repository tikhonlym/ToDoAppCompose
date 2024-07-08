package com.todo.app.todoappcompose.domain.usecase

import android.util.Log
import com.todo.app.todoappcompose.data.AppUserStorage
import com.todo.app.todoappcompose.data.repository.TodoRepositoryImpl
import com.todo.app.todoappcompose.data.room.TaskDao
import com.todo.app.todoappcompose.domain.wrapper.ResultWrapper
import com.todo.app.todoappcompose.domain.wrapper.UIResultWrapper
import com.todo.app.todoappcompose.domain.model.Task
import javax.inject.Inject

/** He is responsible for updating the List<Task> on server by patch request,
 *  if it fails, he will return a local copy.
 *  Error mapping to ui layer.
 */

class UpdateTaskListUseCase @Inject constructor(
    private val repository: TodoRepositoryImpl,
    private val appUserStorage: AppUserStorage,
    private val dao: TaskDao,
) {

    suspend fun execute(): UIResultWrapper<List<Task>> {
        val result = repository.updateTaskList(dao.getTaskList(), appUserStorage.getRevision())
        Log.e("testdqwd", dao.getTaskList().toString(), )
        when (result) {
            is ResultWrapper.Success -> {
                appUserStorage.saveRevision(result.value.revision)
                appUserStorage.saveSyncNeeded(false)
                dao.replaceTable(result.value.list)
                return UIResultWrapper.Success(result.value.list)
            }
            is ResultWrapper.NetworkError -> {
                return UIResultWrapper.SuccessNoInternet(dao.getTaskList())
            }
            else -> {
                Log.w("ServerError", "result: $result")
                return UIResultWrapper.SuccessWithServerError(dao.getTaskList())
            }
        }
    }
}