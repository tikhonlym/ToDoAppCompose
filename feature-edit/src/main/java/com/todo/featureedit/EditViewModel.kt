package com.todo.featureedit

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.todo.domain.model.Task
import com.todo.domain.model.wrapper.UIResultWrapper
import com.todo.domain.usecase.CreateTaskUseCase
import com.todo.domain.usecase.DeleteTaskUseCase
import com.todo.domain.usecase.GetTaskUseCase
import com.todo.domain.usecase.UpdateTaskUseCase
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
class EditViewModel @Inject constructor(
    private val deleteTaskUseCase: DeleteTaskUseCase,
    private val createTaskUseCase: CreateTaskUseCase,
    private val getTaskUseCase: GetTaskUseCase,
    private val updateTaskUseCase: UpdateTaskUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(EditScreenState.Entering)
    val uiState: StateFlow<EditScreenState> get() = _uiState

    private val _currentTask = MutableStateFlow(Constants.emptyTask)
    val currentTask: StateFlow<Task> get() = _currentTask

    private val _deleteButtonActive = MutableStateFlow(false)
    val deleteButtonActive: StateFlow<Boolean> get() = _deleteButtonActive

    private val _circleLoaderActive = MutableStateFlow(false)
    val circleLoaderActive: StateFlow<Boolean> get() = _circleLoaderActive

    private val _onNavigateUpWithResult = MutableStateFlow(false)
    val onNavigateUp: StateFlow<Boolean> get() = _onNavigateUpWithResult

    private val handler = CoroutineExceptionHandler { _, exception ->
        Log.e(TAG, "Caught by handler: $exception")
    }

    private val safeViewModelScope = viewModelScope + handler

    fun initScreen(id: String?) {
        if (id == null) {
            _currentTask.update { Constants.emptyTask }
            initCreatingNewTaskMode()
        } else {
            viewModelScope.launch {
                _currentTask.update { getTask(id) }
                initEditingMode()
            }
        }
    }

    fun deleteTask(id: String) {
        safeViewModelScope.launch {
            _circleLoaderActive.update { true }
            deleteTaskUseCase.execute(id)
            _onNavigateUpWithResult.update { true }
        }
    }

    fun createOrUpdateTask(task: Task, create: Boolean) {
        safeViewModelScope.launch {
            _circleLoaderActive.update { true }
            if (create) {
                createTaskUseCase.execute(task)
            } else {
                updateTaskUseCase.execute(task)
            }
            _onNavigateUpWithResult.update { true }
        }
    }

    private fun initCreatingNewTaskMode() {
        _uiState.value = EditScreenState.CreatingNew
        _deleteButtonActive.update { false }
    }

    private fun initEditingMode() {
        _uiState.value = EditScreenState.Editing
        _deleteButtonActive.update { true }
    }

    private suspend fun getTask(id: String): Task {
        val result = getTaskUseCase.execute(id)
        return when (result) {
            is UIResultWrapper.Success -> result.value
            is UIResultWrapper.SuccessNoInternet -> result.value
            is UIResultWrapper.SuccessWithServerError -> result.value
            is UIResultWrapper.SuccessSynchronizationNeeded -> result.value
        }
    }

    companion object {
        private const val TAG = "EditViewModel"
    }
}

enum class EditScreenState {
    Entering, CreatingNew, Editing
}