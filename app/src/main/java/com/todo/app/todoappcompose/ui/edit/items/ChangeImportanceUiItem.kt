package com.todo.app.todoappcompose.ui.edit.items

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.todo.app.todoappcompose.R
import com.todo.app.todoappcompose.app.theme.AppTheme
import com.todo.app.todoappcompose.domain.model.TaskImportance

@Composable
fun ChangeImportanceUiItem(
    onClick: (TaskImportance) -> Unit,
    modifier: Modifier = Modifier,
    importance: TaskImportance? = null,
) {
    var expandedImportanceMenu by remember { mutableStateOf(false) }
    Column(
        modifier.clickable(
            interactionSource = null,
            indication = null
        ) {
            expandedImportanceMenu = true
        }
    ) {
        ChangeImportanceMenu(
            onChangeImportance = { importance ->
                onClick(importance)
            },
            expandedImportanceMenu = expandedImportanceMenu,
            onChangeExpanded = { state ->
                expandedImportanceMenu = state
            }
        )
        Text(
            text = stringResource(R.string.importance_title),
            color = AppTheme.colorScheme.labelPrimary,
            style = AppTheme.typographyScheme.body
        )
        Spacer(modifier = Modifier.height(4.dp))
        when (importance) {
            TaskImportance.low -> Text(
                text = stringResource(R.string.low_category_title),
                color = AppTheme.colorScheme.labelTertiary,
                style = AppTheme.typographyScheme.subhead
            )

            TaskImportance.important -> Text(
                text = stringResource(R.string.high_category_title),
                color = AppTheme.colorScheme.colorRed,
                style = AppTheme.typographyScheme.subhead
            )

            else -> Text(
                text = stringResource(R.string.no_category_title),
                color = AppTheme.colorScheme.labelTertiary,
                style = AppTheme.typographyScheme.subhead
            )
        }
    }
}

@Composable
private fun ChangeImportanceMenu(
    onChangeImportance: (TaskImportance) -> Unit,
    modifier: Modifier = Modifier,
    expandedImportanceMenu: Boolean,
    onChangeExpanded: (Boolean) -> Unit,
) {
    DropdownMenu(
        modifier = modifier
            .background(AppTheme.colorScheme.backSecondary),
        expanded = expandedImportanceMenu,
        onDismissRequest = { onChangeExpanded(false) },
    ) {
        DropdownMenuItem(
            onClick = {
                onChangeImportance(TaskImportance.basic)
                onChangeExpanded(false)
            },
            text = {
                Text(
                    text = stringResource(R.string.no_category_title),
                    color = AppTheme.colorScheme.labelPrimary,
                    style = AppTheme.typographyScheme.body
                )
            }
        )
        DropdownMenuItem(
            onClick = {
                onChangeImportance(TaskImportance.low)
                onChangeExpanded(false)
            },
            text = {
                Text(
                    text = stringResource(R.string.low_category_title),
                    color = AppTheme.colorScheme.labelPrimary,
                    style = AppTheme.typographyScheme.body
                )
            }
        )
        DropdownMenuItem(
            onClick = {
                onChangeImportance(TaskImportance.important)
                onChangeExpanded(false)
            },
            text = {
                Text(
                    text = stringResource(R.string.high_category_title),
                    color = AppTheme.colorScheme.colorRed,
                    style = AppTheme.typographyScheme.body
                )
            }
        )
    }
}

@Preview
@Composable
private fun ChangeImportanceUiItemPreview() {
    Column {
        AppTheme {
            ChangeImportanceUiItem({})
        }
        AppTheme(darkTheme = true) {
            ChangeImportanceUiItem({})
        }
    }
}