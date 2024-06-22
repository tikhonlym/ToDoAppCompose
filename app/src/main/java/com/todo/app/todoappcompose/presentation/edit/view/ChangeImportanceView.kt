package com.todo.app.todoappcompose.presentation.edit.view

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
import androidx.compose.ui.unit.dp
import com.todo.app.todoappcompose.R
import com.todo.app.todoappcompose.app.theme.AppTheme
import com.todo.app.todoappcompose.data.objects.TodoImportance

@Composable
fun ChangeImportanceView(
    onClick: (TodoImportance) -> Unit,
    modifier: Modifier = Modifier,
    importance: TodoImportance? = null,
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
        DropdownMenu(
            modifier = Modifier
                .background(AppTheme.colorScheme.backSecondary),
            expanded = expandedImportanceMenu,
            onDismissRequest = { expandedImportanceMenu = false },
        ) {
            DropdownMenuItem(
                onClick = {
                    onClick.invoke(TodoImportance.NORMAL)
                    expandedImportanceMenu = false
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
                    onClick.invoke(TodoImportance.LOW)
                    expandedImportanceMenu = false
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
                    onClick.invoke(TodoImportance.HIGH)
                    expandedImportanceMenu = false
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
        Text(
            text = stringResource(R.string.importance_title),
            color = AppTheme.colorScheme.labelPrimary,
            style = AppTheme.typographyScheme.body
        )
        Spacer(modifier = Modifier.height(4.dp))
        when (importance) {
            TodoImportance.LOW -> Text(
                text = stringResource(R.string.low_category_title),
                color = AppTheme.colorScheme.labelTertiary,
                style = AppTheme.typographyScheme.subhead
            )

            TodoImportance.HIGH -> Text(
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