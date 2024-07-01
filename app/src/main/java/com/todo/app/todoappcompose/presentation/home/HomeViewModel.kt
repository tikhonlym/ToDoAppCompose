package com.todo.app.todoappcompose.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.todo.app.todoappcompose.domain.objects.TodoItem
import com.todo.app.todoappcompose.domain.usecase.CompleteTask
import com.todo.app.todoappcompose.domain.usecase.CountCompletedTask
import com.todo.app.todoappcompose.domain.usecase.GetTaskList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getTodoList: GetTaskList,
    private val completeTodoTask: CompleteTask,
    private val countCompletedTask: CountCompletedTask,
) : ViewModel() {

    private val _todoList = MutableStateFlow<List<TodoItem>>(emptyList())
    val todoList: StateFlow<List<TodoItem>> get() = _todoList

    private val _countCompleted = MutableStateFlow<Int>(0)
    val countCompleted: StateFlow<Int> get() = _countCompleted

    init {
        getTodoList()
    }

    private fun getTodoList() {
        // The demonstration of the success/errors requests will be added with the server
        viewModelScope.launch {
            _todoList.value = getTodoList.execute()
            _countCompleted.value = countCompletedTask.execute()
        }
    }

    private val _showCompletedTasks = MutableStateFlow<Boolean>(false)
    val showCompletedTasks: StateFlow<Boolean> get() = _showCompletedTasks

    fun showCompletedTasks(show: Boolean) {
        _showCompletedTasks.value = show
    }

    fun completeTask(id: String, isDone: Boolean) {
        // The demonstration of the success/errors requests will be added with the server
        viewModelScope.launch {
            completeTodoTask.execute(id, isDone)
            getTodoList()
        }
    }
}