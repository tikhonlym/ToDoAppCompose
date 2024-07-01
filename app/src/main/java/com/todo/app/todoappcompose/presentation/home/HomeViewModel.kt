package com.todo.app.todoappcompose.presentation.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.todo.app.todoappcompose.domain.objects.TodoItem
import com.todo.app.todoappcompose.domain.usecase.CompleteTaskUseCase
import com.todo.app.todoappcompose.domain.usecase.CountCompletedTaskUseCase
import com.todo.app.todoappcompose.domain.usecase.GetTaskListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getTodoListUseCase: GetTaskListUseCase,
    private val completeTodoTaskUseCase: CompleteTaskUseCase,
    private val countCompletedTaskUseCase: CountCompletedTaskUseCase,
) : ViewModel() {

    private val _todoList = MutableStateFlow<List<TodoItem>>(emptyList())
    val todoList: StateFlow<List<TodoItem>> get() = _todoList

    private val _countCompleted = MutableStateFlow<Int>(0)
    val countCompleted: StateFlow<Int> get() = _countCompleted

    private val _showCompletedTasks = MutableStateFlow<Boolean>(false)
    val showCompletedTasks: StateFlow<Boolean> get() = _showCompletedTasks

    private val handler = CoroutineExceptionHandler { _, exception ->
        Log.e(TAG, "Caught by handler: $exception")
    }

    private val safeViewModelScope = viewModelScope + handler

    init {
        getTodoList()
    }

    fun showCompletedTasks(state: Boolean) {
        _showCompletedTasks.update { state }
    }

    fun completeTask(id: String, isDone: Boolean) {
        // The demonstration of the success/errors requests will be added with the server
        safeViewModelScope.launch {
            completeTodoTaskUseCase.execute(id, isDone)
            getTodoList()
        }
    }

    fun countCompletedTask() {
        safeViewModelScope.launch {
            _countCompleted.update {
                countCompletedTaskUseCase.execute()
            }
        }
    }

    private fun getTodoList() {
        // The demonstration of the success/errors requests will be added with the server
        safeViewModelScope.launch {
            countCompletedTask()
            _todoList.update {
                getTodoListUseCase.execute()
            }
        }
    }

    companion object {
        private const val TAG = "HomeViewModel"
    }
}