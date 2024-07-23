package com.todo.featurehome

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.todo.core.util.getDeviceName
import com.todo.core.theme.component.SnackBarData
import com.todo.domain.model.Task
import com.todo.domain.model.wrapper.UIResultWrapper
import com.todo.domain.usecase.GetLocalListUseCase
import com.todo.domain.usecase.GetTaskListUseCase
import com.todo.domain.usecase.UpdateTaskListUseCase
import com.todo.domain.usecase.UpdateTaskUseCase
import com.todo.domain.usecase.sync.GetSyncNeededUseCase
import com.todo.domain.usecase.sync.UpdateSyncNeededUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus
import javax.inject.Inject

/** Responsible for the state of the screen - EditScreen */

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getLocalListUseCase: GetLocalListUseCase,
    private val getTaskListUseCase: GetTaskListUseCase,
    private val updateTaskUseCase: UpdateTaskUseCase,
    private val updateTaskListUseCase: UpdateTaskListUseCase,
    private val syncNeededUseCase: GetSyncNeededUseCase,
    private val updateSyncNeededUseCase: UpdateSyncNeededUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeScreenState.Loading)
    val uiState: StateFlow<HomeScreenState> get() = _uiState

    private val _showSnackBar = MutableStateFlow<SnackBarData?>(null)
    val showSnackBar: StateFlow<SnackBarData?> get() = _showSnackBar

    private val _taskList = MutableStateFlow<List<Task>>(emptyList())
    val taskList: StateFlow<List<Task>> get() = _taskList

    private val _countCompleted = MutableStateFlow(0)
    val countCompleted: StateFlow<Int> get() = _countCompleted

    private val _showCompletedTasks = MutableStateFlow(false)
    val showCompletedTasks: StateFlow<Boolean> get() = _showCompletedTasks

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean> get() = _isRefreshing

    private val _syncNeeded = MutableStateFlow(false)
    val syncNeeded: StateFlow<Boolean> get() = _syncNeeded

    private val handler = CoroutineExceptionHandler { _, exception ->
        Log.e(TAG, "Caught by handler: $exception")
    }

    private val safeViewModelScope = viewModelScope + handler

    init {
        getTaskList()
        safeViewModelScope.launch {
            taskList.collect {
                countCompletedTask()
            }
        }
    }

    fun showCompletedTasks(state: Boolean) {
        _showCompletedTasks.update { state }
    }

    fun completeTask(task: Task, isDone: Boolean) {
        safeViewModelScope.launch {
            val result = updateTaskUseCase.execute(
                task = task.copy(
                    done = isDone,
                    last_updated_by = getDeviceName()
                )
            )
            when (result) {
                is UIResultWrapper.Success -> updateListWithTask(result.value)
                is UIResultWrapper.SuccessNoInternet -> updateListWithTask(result.value)
                is UIResultWrapper.SuccessSynchronizationNeeded -> {
                    updateListWithTask(result.value)
                    _showSnackBar.update { SnackBarData(text = "Необходима синхронизация") }
                }

                is UIResultWrapper.SuccessWithServerError -> {
                    updateListWithTask(result.value)
                    _showSnackBar.update {
                        SnackBarData("Ошибка сервера")
                    }
                }
            }
            checkSyncNeeded()
        }
    }

    fun refreshList() {
        safeViewModelScope.launch {
            getTaskList(true)
            if (syncNeededUseCase.execute()) {
                synchronizeTaskListWithServer()
            }
            checkSyncNeeded()
        }
    }

    fun getLocalList() {
        safeViewModelScope.launch {
            _taskList.update { getLocalListUseCase.execute() }
        }
    }

    fun checkSyncNeeded(): Boolean {
        val syncNeeded = syncNeededUseCase.execute()
        _syncNeeded.update { syncNeeded }
        return syncNeeded
    }

    private fun synchronizeTaskListWithServer() {
        safeViewModelScope.launch {
            val result = updateTaskListUseCase.execute()
            if (result is UIResultWrapper.Success) {
                _taskList.update { result.value }
            }
            checkSyncNeeded()
        }
    }

    private fun getTaskList(refreshing: Boolean = false) {
        safeViewModelScope.launch {
            if (refreshing) _isRefreshing.update { true } else _uiState.update { HomeScreenState.Loading }
            when (val result = getTaskListUseCase.execute()) {
                is UIResultWrapper.Success -> _taskList.update { result.value }
                is UIResultWrapper.SuccessNoInternet -> {
                    _taskList.update { result.value }
                }

                is UIResultWrapper.SuccessWithServerError -> {
                    _taskList.update { result.value }
                    _showSnackBar.update {
                        SnackBarData("Ошибка сервера")
                    }
                }

                is UIResultWrapper.SuccessSynchronizationNeeded -> {
                    _taskList.update { result.value }
                }
            }
            if (refreshing) _isRefreshing.update { false } else _uiState.update { HomeScreenState.Complete }
            checkSyncNeeded()
        }
    }

    private fun countCompletedTask() {
        _countCompleted.update {
            taskList.value.count { it.done }
        }
    }

    private fun updateListWithTask(taskToUpdate: Task) {
        _taskList.update { currentList ->
            val newList = currentList.toMutableList()
            val existingTaskIndex = newList.indexOfFirst { it.id == taskToUpdate.id }

            if (existingTaskIndex != -1) {
                newList[existingTaskIndex] = taskToUpdate
            } else {
                newList.add(taskToUpdate)
            }
            newList
        }
    }

    override fun onCleared() {
        super.onCleared()
        updateSyncNeededUseCase.execute(false)
    }

    companion object {
        private const val TAG = "HomeViewModel"
    }
}

enum class HomeScreenState {
    Loading, Complete
}