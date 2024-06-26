package com.todo.app.todoappcompose.presentation.edit

import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.todo.app.todoappcompose.R
import com.todo.app.todoappcompose.app.theme.AppTheme
import com.todo.app.todoappcompose.data.objects.TaskDate
import com.todo.app.todoappcompose.data.objects.TodoItem
import com.todo.app.todoappcompose.presentation.Constants
import com.todo.app.todoappcompose.presentation.edit.view.ChangeImportanceView
import com.todo.app.todoappcompose.presentation.edit.view.DeleteTaskButton
import kotlinx.serialization.Serializable


@Serializable
data class EditScreenDestination(
    val id: String?,
)

@Composable
fun EditScreen(
    onNavigateBack: () -> Unit,
    viewModel: EditTodoViewModel = hiltViewModel(),
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


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeadLineView(
    modifier: Modifier = Modifier,
    deadline: TaskDate?,
) {
    var isDeadlineActive by remember { mutableStateOf(deadline != null) }
    var date by remember { mutableStateOf(TaskDate(0L)) }

    var dateDialogController by remember { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState()

    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = Modifier.clickable(
                interactionSource = null,
                indication = null
            ) {
                if (isDeadlineActive)
                    dateDialogController = true
            },
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = stringResource(R.string.deadline_title),
                color = AppTheme.colorScheme.labelPrimary,
                style = AppTheme.typographyScheme.body
            )
            AnimatedVisibility(visible = isDeadlineActive) {
                Text(
                    date.toString(),
                    color = AppTheme.colorScheme.colorBlue,
                    style = AppTheme.typographyScheme.subhead
                )
            }
        }
        Switch(
            checked = isDeadlineActive,
            onCheckedChange = {
                if (!it) {
                    isDeadlineActive = false
                }
                if (it) {
                    dateDialogController = true
                }
            },
            colors = SwitchDefaults.colors(
                checkedThumbColor = AppTheme.colorScheme.colorBlue,
                checkedTrackColor = AppTheme.colorScheme.colorLightBlue,
                checkedBorderColor = Color.Transparent,
                uncheckedThumbColor = AppTheme.colorScheme.backElevated,
                uncheckedTrackColor = AppTheme.colorScheme.supportOverlay,
                uncheckedBorderColor = Color.Transparent,
            )
        )
    }

    if (dateDialogController) {
        DatePickerDialog(
            colors = DatePickerDefaults.colors(
                containerColor = AppTheme.colorScheme.backSecondary
            ),
            onDismissRequest = { dateDialogController = false },
            confirmButton = {
                TextButton(onClick = {
                    dateDialogController = false
                    if (datePickerState.selectedDateMillis != null) {
                        isDeadlineActive = true
                        date = TaskDate(datePickerState.selectedDateMillis!!)
                    }
                }) {
                    Text(
                        text = stringResource(R.string.done_txt),
                        color = AppTheme.colorScheme.colorBlue,
                        style = AppTheme.typographyScheme.button
                    )
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    dateDialogController = false
                }) {
                    Text(
                        text = stringResource(R.string.cancel_txt),
                        color = AppTheme.colorScheme.colorBlue,
                        style = AppTheme.typographyScheme.button
                    )
                }
            }
        ) {
            DatePicker(
                state = datePickerState,
                colors = DatePickerDefaults.colors(
                    todayContentColor = AppTheme.colorScheme.colorBlue,
                    todayDateBorderColor = AppTheme.colorScheme.colorBlue,
                    dayContentColor = AppTheme.colorScheme.labelPrimary,
                    selectedDayContentColor = AppTheme.colorScheme.colorWhite,
                    selectedDayContainerColor = AppTheme.colorScheme.colorBlue,
                )
            )
        }
    }
}


























