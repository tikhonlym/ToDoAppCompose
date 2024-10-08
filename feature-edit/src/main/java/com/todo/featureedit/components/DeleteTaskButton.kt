package com.todo.featureedit.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.todo.core.R
import com.todo.core.theme.AppTheme

@Composable
fun DeleteTaskButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    active: Boolean,
) {
    var onClickEnabled by remember { mutableStateOf(active) }
    val description = stringResource(id = R.string.delete_task_text)
    Row(
        modifier = modifier
            .clickable(
                enabled = onClickEnabled,
                interactionSource = null,
                indication = null
            ) {
                onClick()
                onClickEnabled = false
            }
            .semantics {
                contentDescription = description
            }
            .testTag("deleteTask"),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier.size(24.dp),
            painter = painterResource(R.drawable.ic_delete),
            tint = if (active) AppTheme.colorScheme.colorRed else AppTheme.colorScheme.supportSeparator,
            contentDescription = null
        )
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text = stringResource(R.string.delete_title),
            style = AppTheme.typographyScheme.body,
            color = if (active) AppTheme.colorScheme.colorRed else AppTheme.colorScheme.supportSeparator
        )
    }
}

@Preview
@Composable
private fun DeleteTaskButtonPreview() {
    Column {
        AppTheme {
            DeleteTaskButton({}, active = true)
        }
        AppTheme() {
            DeleteTaskButton({}, active = false)
        }
    }
}