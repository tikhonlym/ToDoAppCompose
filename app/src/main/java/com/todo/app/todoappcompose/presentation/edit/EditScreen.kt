package com.todo.app.todoappcompose.presentation.edit

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.todo.app.todoappcompose.R
import com.todo.app.todoappcompose.app.theme.AppTheme
import com.todo.app.todoappcompose.data.repository.todo.TodoItemsRepositoryImpl
import com.todo.app.todoappcompose.domain.objects.TodoItem
import com.todo.app.todoappcompose.domain.usecase.CreateTask
import com.todo.app.todoappcompose.domain.usecase.DeleteTask
import com.todo.app.todoappcompose.domain.usecase.GetTask
import com.todo.app.todoappcompose.domain.usecase.UpdateTaskDeadline
import com.todo.app.todoappcompose.domain.usecase.UpdateTaskImportance
import com.todo.app.todoappcompose.domain.usecase.UpdateTaskText
import com.todo.app.todoappcompose.presentation.Constants
import com.todo.app.todoappcompose.presentation.edit.view.ChangeImportanceView
import com.todo.app.todoappcompose.presentation.edit.view.DeadLineView
import com.todo.app.todoappcompose.presentation.edit.view.DeleteTaskButton
import kotlinx.serialization.Serializable


@Serializable
data class EditScreenDestination(
    val id: String?,
)

@Composable
fun EditScreen(
    onNavigateBack: () -> Unit,
    viewModel: EditViewModel = hiltViewModel(),
    id: String? = null,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AppTheme.colorScheme.backPrimary),
    ) {

        val todoTask: TodoItem = if (id == null) {
            Constants.emptyTask
        } else {
            viewModel.getTask(id)
        }

        var importanceState by remember {
            mutableStateOf(todoTask.importance)
        }

        EditToolBar(
            onClose = {
                onNavigateBack()
            },
            onSaveTask = {
                onNavigateBack()
            }
        )

        Divider(
            modifier = Modifier.shadow(elevation = 4.dp),
            color = AppTheme.colorScheme.supportSeparator
        )

        Column(
            Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(modifier = Modifier.height(8.dp))

            var textFieldValue by remember { mutableStateOf(TextFieldValue(todoTask.text)) }

            BasicTextField(
                modifier = Modifier
                    .defaultMinSize(minHeight = 100.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(AppTheme.colorScheme.backSecondary),
                value = textFieldValue,
                textStyle = AppTheme.typographyScheme.body
                    .copy(color = AppTheme.colorScheme.labelPrimary),
                onValueChange = { textFieldValue = it },
                decorationBox = { innerTextField ->
                    Row(
                        Modifier
                            .padding(16.dp)
                            .fillMaxWidth()
                    ) {

                        if (textFieldValue.text.isEmpty()) {
                            Text(
                                text = stringResource(R.string.hint_text_your),
                                style = AppTheme.typographyScheme.body,
                                color = AppTheme.colorScheme.supportSeparator
                            )
                        }
                        innerTextField()
                    }
                },
            )

            Spacer(modifier = Modifier.height(12.dp))

            ChangeImportanceView(
                modifier = Modifier.padding(top = 16.dp, bottom = 16.dp),
                importance = importanceState,
                onClick = { importance ->
                    importanceState = importance
                }
            )

            Divider(color = AppTheme.colorScheme.supportSeparator)

            DeadLineView(
                modifier = Modifier.padding(top = 16.dp, bottom = 16.dp),
                todoTask.deadline
            )

            Divider(color = AppTheme.colorScheme.supportSeparator)

            DeleteTaskButton(
                modifier = Modifier.padding(top = 12.dp, bottom = 12.dp),
                onClick = {
                    onNavigateBack()
                }
            )

            Spacer(modifier = Modifier.height(48.dp))
        }
    }
}

@Preview
@Composable
private fun EditScreenPreview() {
    AppTheme {
        EditScreen(
            {}, id = null,
            viewModel = EditViewModel(
                deleteTaskUseCase = DeleteTask(TodoItemsRepositoryImpl()),
                updateTaskTextUseCase = UpdateTaskText(TodoItemsRepositoryImpl()),
                updateTaskImportanceUseCase = UpdateTaskImportance(TodoItemsRepositoryImpl()),
                updateTaskDeadlineUseCase = UpdateTaskDeadline(TodoItemsRepositoryImpl()),
                createTaskUseCase = CreateTask(TodoItemsRepositoryImpl()),
                getTaskUseCase = GetTask(TodoItemsRepositoryImpl())
            )
        )
    }
}

@Preview
@Composable
private fun EditScreenPreviewDark() {
    AppTheme(darkTheme = true) {
        EditScreen(
            {}, id = null,
            viewModel = EditViewModel(
                deleteTaskUseCase = DeleteTask(TodoItemsRepositoryImpl()),
                updateTaskTextUseCase = UpdateTaskText(TodoItemsRepositoryImpl()),
                updateTaskImportanceUseCase = UpdateTaskImportance(TodoItemsRepositoryImpl()),
                updateTaskDeadlineUseCase = UpdateTaskDeadline(TodoItemsRepositoryImpl()),
                createTaskUseCase = CreateTask(TodoItemsRepositoryImpl()),
                getTaskUseCase = GetTask(TodoItemsRepositoryImpl())
            )
        )
    }

}

@Composable
private fun EditToolBar(
    onClose: () -> Unit,
    onSaveTask: () -> Unit,
    modifier: Modifier = Modifier,
) {
    var onClickEnabled by remember { mutableStateOf(true) }
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(AppTheme.colorScheme.backPrimary)
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier
                .size(24.dp)
                .clickable(
                    enabled = onClickEnabled,
                    interactionSource = null,
                    indication = null
                ) {
                    onClose.invoke()
                    onClickEnabled = false
                },
            tint = AppTheme.colorScheme.labelPrimary,
            painter = painterResource(R.drawable.ic_close),
            contentDescription = null,
        )
        Text(
            modifier = Modifier.clickable(
                enabled = onClickEnabled,
                interactionSource = null,
                indication = null
            ) {
                onSaveTask.invoke()
                onClickEnabled = false
            },
            text = stringResource(R.string.btn_save_title),
            color = AppTheme.colorScheme.colorBlue,
            style = AppTheme.typographyScheme.button
        )
    }
}