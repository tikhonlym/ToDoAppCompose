package com.todo.app.todoappcompose.presentation.edit.view

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.todo.app.todoappcompose.R
import com.todo.app.todoappcompose.app.theme.AppTheme
import com.todo.app.todoappcompose.domain.objects.TaskDate

@Composable
fun DeadLineView(
    modifier: Modifier = Modifier,
    deadline: TaskDate?,
) {
    var isDeadlineActive by remember { mutableStateOf(deadline != null) }
    var deadlineDate by remember { mutableStateOf(TaskDate(0L)) }

    var dateDialogController by remember { mutableStateOf(false) }

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
                    text = deadlineDate.toString(),
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
        MyDatePickerDialog(
            onChangeController = { state ->
                dateDialogController = state
            },
            onChangeDeadlineDate = { date ->
                deadlineDate = date
            },
            onChangeDeadlineActive = { state ->
                isDeadlineActive = state
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun DeadLineViewPreview() {
    Column {
        AppTheme {
            Column {
                DeadLineView(deadline = TaskDate(0))
                DeadLineView(deadline = null)
            }
        }
    }
}