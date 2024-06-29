package com.todo.app.todoappcompose.data.repository.todo

import com.todo.app.todoappcompose.data.source.local.TestData
import com.todo.app.todoappcompose.domain.objects.TodoItem
import com.todo.app.todoappcompose.domain.repository.TodoItemsRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

/**
 * localTestData: MutableList<TodoItem> - Created only for test logic!
 * it will be deleted soon..
 * **/
class TodoItemsRepositoryImpl(
    private val localTestData: MutableList<TodoItem> = TestData.dataList,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
) : TodoItemsRepository {

    override suspend fun getTaskList(): List<TodoItem> = CoroutineScope(dispatcher).async {
        localTestData
    }.await()

    override suspend fun completeTask(id: String, isDone: Boolean) {
        CoroutineScope(dispatcher).launch {
            val indexCompletedElement = localTestData.indexOfFirst {
                it.id == id
            }
            val curTask = localTestData[indexCompletedElement]
            localTestData[indexCompletedElement] = curTask.copy(isDone = isDone)
        }
    }

    override suspend fun countCompletedTask() = CoroutineScope(dispatcher).async {
        localTestData.count { it.isDone }
    }.await()

    override suspend fun getTask(id: String) = CoroutineScope(dispatcher).async {
        localTestData.first { it.id == id }
    }.await()

    override suspend fun deleteTask(id: String) {
        CoroutineScope(dispatcher).launch {
            val index = localTestData.indexOfFirst { it.id == id }
            localTestData.removeAt(index)
        }
    }

    override suspend fun createOrUpdateTask(task: TodoItem) {
        CoroutineScope(dispatcher).launch {
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

