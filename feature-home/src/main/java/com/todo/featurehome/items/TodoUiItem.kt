package com.todo.featurehome.items

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.todo.core.R
import com.todo.core.util.formatToString
import com.todo.core.util.millisecondsToLocalDate
import com.todo.core.theme.AppTheme
import com.todo.domain.model.Task
import com.todo.domain.model.TaskImportance
import java.time.LocalDate

@Composable
fun TodoUiItem(
    onCompleteClick: (task: Task, isDone: Boolean) -> Unit,
    onItemClicked: (id: String) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean,
    showCompletedTasks: Boolean,
    data: Task,
) {
    var completeState by remember { mutableStateOf(data.done) }
    AnimatedVisibility(!(!showCompletedTasks && completeState)) {
        Box(
            modifier
                .fillMaxWidth()
                .clickable(enabled = enabled) {
                    onItemClicked(data.id)
                }
                .background(AppTheme.colorScheme.backSecondary)
                .padding(
                    start = 16.dp,
                    end = 16.dp,
                    top = 12.dp,
                    bottom = 12.dp
                ),
        ) {
            Row(
                verticalAlignment = Alignment.Top
            ) {
                Checkbox(
                    modifier = Modifier
                        .size(24.dp),
                    checked = completeState,
                    onCheckedChange = {
                        completeState = it
                        onCompleteClick.invoke(data, completeState)
                    },
                    colors = if (completeState) {
                        CheckboxDefaults.colors(
                            checkedColor = AppTheme.colorScheme.colorGreen,
                            checkmarkColor = AppTheme.colorScheme.backSecondary,
                        )
                    } else {
                        if (data.importance == TaskImportance.important) {
                            CheckboxDefaults.colors(
                                uncheckedColor = AppTheme.colorScheme.colorRed,
                                checkmarkColor = AppTheme.colorScheme.colorRed,
                            )
                        } else {
                            CheckboxDefaults.colors(
                                uncheckedColor = AppTheme.colorScheme.supportSeparator
                            )
                        }
                    }
                )
                Spacer(modifier = Modifier.width(12.dp))
                if (data.importance != TaskImportance.basic && !completeState) {
                    Icon(
                        painter = painterResource(
                            id = if (data.importance == TaskImportance.low) {
                                R.drawable.ic_low_importance
                            } else {
                                R.drawable.ic_urgently_importance
                            }
                        ),
                        tint = if (data.importance == TaskImportance.low) {
                            AppTheme.colorScheme.colorGray
                        } else {
                            AppTheme.colorScheme.colorRed
                        },
                        contentDescription = null
                    )
                    Spacer(modifier = Modifier.width(3.dp))
                }

                ItemText(
                    completeState = completeState,
                    modifier = Modifier.padding(end = 36.dp, top = 2.dp),
                    text = data.text,
                    deadline = data.deadline?.let { millisecondsToLocalDate(it) }
                )
            }
            Icon(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .size(24.dp),
                painter = painterResource(id = R.drawable.ic_info_outline),
                contentDescription = null,
                tint = AppTheme.colorScheme.labelTertiary
            )
        }
    }
}

@Composable
private fun ItemText(
    completeState: Boolean,
    modifier: Modifier = Modifier,
    text: String,
    deadline: LocalDate?,
) {
    Column {
        Text(
            modifier = modifier,
            text = text,
            color = if (completeState) {
                AppTheme.colorScheme.labelTertiary
            } else {
                AppTheme.colorScheme.labelPrimary
            },
            style = if (completeState) {
                AppTheme.typographyScheme.body.copy(textDecoration = TextDecoration.LineThrough)
            } else {
                AppTheme.typographyScheme.body
            },
            maxLines = 3,
            overflow = TextOverflow.Ellipsis
        )

        AnimatedVisibility(visible = deadline != null && !completeState) {
            deadline?.let {
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = deadline.formatToString(),
                    style = AppTheme.typographyScheme.subhead,
                    color = AppTheme.colorScheme.labelTertiary
                )
            }
        }
    }
}

@Preview
@Composable
private fun TodoUiItemPreview() {
    AppTheme() {
        TodoUiItem(
            onCompleteClick = { id, isDone -> },
            onItemClicked = { },
            modifier = Modifier,
            enabled = false,
            showCompletedTasks = true,
            data = Task(
                id = "test",
                text = "Test testTest testTest testTest testTest testTest testTest test",
                importance = TaskImportance.basic,
                deadline = null,
                done = true,
                created_at = 0L,
                changed_at = 0L,
                last_updated_by = ""
            ),
        )
    }
}

@Preview
@Composable
private fun TodoUiItemPreviewDark() {
    AppTheme(darkTheme = true) {
        TodoUiItem(
            onCompleteClick = { id, isDone -> },
            onItemClicked = { },
            modifier = Modifier,
            enabled = false,
            showCompletedTasks = true,
            data = Task(
                id = "test",
                text = "Test testTest testTest testTest testTest testTest testTest test",
                importance = TaskImportance.important,
                deadline = 0,
                done = false,
                created_at = 0L,
                changed_at = 0L,
                last_updated_by = ""
            ),
        )
    }
}