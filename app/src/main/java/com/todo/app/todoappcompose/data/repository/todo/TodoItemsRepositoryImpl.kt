package com.todo.app.todoappcompose.data.repository.todo

import com.todo.app.todoappcompose.app.dispatchers.AppDispatchers
import com.todo.app.todoappcompose.data.source.local.TestData
import com.todo.app.todoappcompose.domain.objects.TodoItem
import kotlinx.coroutines.withContext

/**
 * localTestData: MutableList<TodoItem> - Created only for test logic!
 * it will be deleted soon..
 * **/
class TodoItemsRepositoryImpl(
    private val localTestData: MutableList<TodoItem> = TestData.dataList,
    private val dispatcher: AppDispatchers,
) {

    suspend fun getTaskList(): List<TodoItem> = withContext(dispatcher.io) {
        localTestData
    }

    suspend fun completeTask(id: String, isDone: Boolean) {
        withContext(dispatcher.io) {
            val indexCompletedElement = localTestData.indexOfFirst {
                it.id == id
            }
            val curTask = localTestData[indexCompletedElement]
            localTestData[indexCompletedElement] = curTask.copy(isDone = isDone)
        }
    }

    suspend fun countCompletedTask() = withContext(dispatcher.io) {
        localTestData.count { it.isDone }
    }

    suspend fun getTask(id: String) = withContext(dispatcher.io) {
        localTestData.first { it.id == id }
    }

    suspend fun deleteTask(id: String) {
        withContext(dispatcher.io) {
            val index = localTestData.indexOfFirst { it.id == id }
            localTestData.removeAt(index)
        }
    }

    suspend fun createOrUpdateTask(task: TodoItem) {
        withContext(dispatcher.io) {
            val index = localTestData.indexOfFirst { it.id == task.id }
            if (index == NON_EXISTENT_INDEX) {
                localTestData.add(FIRST_INDEX, task)
            } else {
                localTestData[index] = task
            }
        }
    }

    companion object {
        private const val NON_EXISTENT_INDEX = -1
        private const val FIRST_INDEX = 0
    }
}