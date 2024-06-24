package com.todo.app.todoappcompose.data.repository.todo

import com.todo.app.todoappcompose.data.objects.TaskDate
import com.todo.app.todoappcompose.data.objects.TodoImportance
import com.todo.app.todoappcompose.data.objects.TodoItem
import com.todo.app.todoappcompose.domain.repository.TodoItemsRepository

/**
 * localTestData: MutableList<TodoItem> - Created only for test logic!
 * it will be deleted soon..
 * **/
class TodoItemsRepositoryImpl(
    private val localTestData: MutableList<TodoItem> = TestData.dataList,
) : TodoItemsRepository {

    override fun getTaskList(): List<TodoItem> = localTestData

    override fun completeTask(id: String, isDone: Boolean) {
        val indexCompletedElement = localTestData.indexOfFirst {
            it.id == id
        }
        val curTask = localTestData[indexCompletedElement]
        localTestData[indexCompletedElement] = curTask.copy(isDone = isDone)
    }

    override fun countCompletedTask() = localTestData.count { it.isDone }

    override fun getTask(id: String) = localTestData.first { it.id == id }

    override fun deleteTask(id: String) {
        val index = localTestData.indexOfFirst { it.id == id }
        localTestData.removeAt(index)
    }

    override fun updateTaskDeadline(id: String, date: TaskDate) {
        val indexCompletedElement = localTestData.indexOfFirst {
            it.id == id
        }
        val curTask = localTestData[indexCompletedElement]
        localTestData[indexCompletedElement] = curTask.copy(deadline = date)
    }


    override fun updateTaskText(id: String, text: String) {
        val indexCompletedElement = localTestData.indexOfFirst {
            it.id == id
        }
        val curTask = localTestData[indexCompletedElement]
        localTestData[indexCompletedElement] = curTask.copy(text = text)
    }


    override fun updateTaskImportance(id: String, importance: TodoImportance) {
        val indexCompletedElement = localTestData.indexOfFirst {
            it.id == id
        }
        val curTask = localTestData[indexCompletedElement]
        localTestData[indexCompletedElement] = curTask.copy(importance = importance)
    }

    override fun createTask(task: TodoItem) {
        localTestData.add(task)
    }

}