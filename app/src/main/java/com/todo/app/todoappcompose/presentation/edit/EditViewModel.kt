package com.todo.app.todoappcompose.presentation.edit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.todo.app.todoappcompose.domain.objects.TodoItem
import com.todo.app.todoappcompose.domain.usecase.CreateOrUpdateTaskUseCase
import com.todo.app.todoappcompose.domain.usecase.DeleteTaskUseCase
import com.todo.app.todoappcompose.domain.usecase.GetTaskUseCase
import com.todo.app.todoappcompose.presentation.util.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditViewModel @Inject constructor(
    private val deleteTaskUseCase: DeleteTaskUseCase,
    private val createOrUpdateTaskUseCase: CreateOrUpdateTaskUseCase,
    private val getTaskUseCase: GetTaskUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow<EditScreenState>(EditScreenState.Loading)
    val uiState: StateFlow<EditScreenState> get() = _uiState

    private val _currentTask = MutableStateFlow<TodoItem>(Constants.emptyTask)
    val currentTask: StateFlow<TodoItem> get() = _currentTask

    private val _onDeleteButtonActive = MutableStateFlow<Boolean>(false)
    val onDeleteButtonActive: StateFlow<Boolean> get() = _onDeleteButtonActive

    init {
        _uiState.update { EditScreenState.Loading }
    }

    fun getTask(id: String?) {
        if (id == null) {
            _currentTask.update { Constants.emptyTask }
            initCreateNewTaskMode()
        } else {
            viewModelScope.launch {
                delay(700L) // Implemented to demonstrate the work
                _currentTask.update { getTaskUseCase.execute(id) }
                initEditingMode()
            }
        }
    }

    fun deleteTask(id: String) {
        // The demonstration of the success/errors requests will be added with the server
        viewModelScope.launch {
            deleteTaskUseCase.execute(id)
        }
    }

    fun createOrUpdateTask(task: TodoItem) {
        // The demonstration of the success/errors requests will be added with the server
        viewModelScope.launch {
            createOrUpdateTaskUseCase.execute(task)
        }
    }

    private fun initCreateNewTaskMode() {
        _uiState.value = EditScreenState.CreatingNew
        _onDeleteButtonActive.update { false }
    }

    private fun initEditingMode() {
        _uiState.value = EditScreenState.Editing
        _onDeleteButtonActive.update { true }
    }
}

enum class EditScreenState {
    Loading, CreatingNew, Editing
}