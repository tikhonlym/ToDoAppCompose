package com.todo.app.todoappcompose.presentation.edit.view

import androidx.compose.foundation.clickable
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.todo.app.todoappcompose.R
import com.todo.app.todoappcompose.app.theme.AppTheme

@Composable
fun DeleteTaskButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    var onClickEnabled by remember { mutableStateOf(true) }
    Row(
        modifier = modifier.clickable(
            enabled = onClickEnabled,
            interactionSource = null,
            indication = null
        ) {
            onClick.invoke()
            onClickEnabled = false
        },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier.size(24.dp),
            painter = painterResource(R.drawable.ic_delete),
            tint = AppTheme.colorScheme.colorRed,
            contentDescription = null
        )
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text = stringResource(R.string.delete_title),
            style = AppTheme.typographyScheme.body,
            color = AppTheme.colorScheme.colorRed
        )
    }
}