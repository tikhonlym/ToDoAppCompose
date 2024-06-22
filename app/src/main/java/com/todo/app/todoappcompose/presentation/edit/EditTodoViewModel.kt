package com.todo.app.todoappcompose.presentation.edit

import androidx.lifecycle.ViewModel
import com.todo.app.todoappcompose.domain.usecase.CreateTask
import com.todo.app.todoappcompose.domain.usecase.DeleteTask
import com.todo.app.todoappcompose.domain.usecase.GetTask
import com.todo.app.todoappcompose.domain.usecase.UpdateTaskDeadline
import com.todo.app.todoappcompose.domain.usecase.UpdateTaskImportance
import com.todo.app.todoappcompose.domain.usecase.UpdateTaskText
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class EditTodoViewModel @Inject constructor(
    private val deleteTaskUseCase: DeleteTask,
    private val updateTaskTextUseCase: UpdateTaskText,
    private val updateTaskImportanceUseCase: UpdateTaskImportance,
    private val updateTaskDeadlineUseCase: UpdateTaskDeadline,
    private val createTaskUseCase: CreateTask,
    private val getTaskUseCase: GetTask,
) : ViewModel() {

    // Will be implemented soon ->

    fun getTask(id: String) = getTaskUseCase.execute(id)

}