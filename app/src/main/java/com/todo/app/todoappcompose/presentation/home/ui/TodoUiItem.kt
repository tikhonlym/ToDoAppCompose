package com.todo.app.todoappcompose.presentation.home.ui

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
import com.todo.app.todoappcompose.R
import com.todo.app.todoappcompose.app.theme.AppTheme
import com.todo.app.todoappcompose.domain.objects.TodoImportance
import com.todo.app.todoappcompose.domain.objects.TodoItem
import com.todo.app.todoappcompose.presentation.util.millisecondsToLocalDate
import java.time.LocalDate

@Composable
fun TodoUiItem(
    onCompleteClick: (id: String, isDone: Boolean) -> Unit,
    onItemClicked: (id: String) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean,
    showCompletedTasks: Boolean,
    data: TodoItem,
) {
    var completeState by remember { mutableStateOf(data.isDone) }
    AnimatedVisibility(!(!showCompletedTasks && completeState)) {
        Box(
            modifier
                .fillMaxWidth()
                .clickable(enabled = enabled) {
                    onItemClicked.invoke(data.id)
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
                        onCompleteClick.invoke(data.id, completeState)
                    },
                    colors = if (completeState) {
                        CheckboxDefaults.colors(
                            checkedColor = AppTheme.colorScheme.colorGreen,
                            checkmarkColor = AppTheme.colorScheme.backSecondary,
                        )
                    } else {
                        if (data.importance == TodoImportance.HIGH) {
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
                if (data.importance != TodoImportance.NORMAL && !completeState) {
                    Icon(
                        painter = painterResource(
                            id = if (data.importance == TodoImportance.LOW) {
                                R.drawable.ic_low_importance
                            } else {
                                R.drawable.ic_urgently_importance
                            }
                        ),
                        tint = if (data.importance == TodoImportance.LOW) {
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
                    deadline = data.deadline
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
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = deadline.toString(),
                style = AppTheme.typographyScheme.subhead,
                color = AppTheme.colorScheme.labelTertiary
            )
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
            data = TodoItem(
                id = "test",
                text = "Test testTest testTest testTest testTest testTest testTest test",
                importance = TodoImportance.NORMAL,
                deadline = null,
                isDone = true,
                creationDate = millisecondsToLocalDate(0L)
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
            data = TodoItem(
                id = "test",
                text = "Test testTest testTest testTest testTest testTest testTest test",
                importance = TodoImportance.HIGH,
                deadline = millisecondsToLocalDate(0L),
                isDone = false,
                creationDate = millisecondsToLocalDate(0L)
            ),
        )
    }
}