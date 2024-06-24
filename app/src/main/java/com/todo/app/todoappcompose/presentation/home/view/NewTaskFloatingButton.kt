package com.todo.app.todoappcompose.presentation.home.view

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.todo.app.todoappcompose.R
import com.todo.app.todoappcompose.app.theme.AppTheme

@Composable
fun NewTaskFloatingButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    FloatingActionButton(
        onClick = { onClick.invoke() },
        shape = CircleShape,
        modifier = modifier
            .size(56.dp),
        contentColor = AppTheme.colorScheme.colorWhite,
        containerColor = AppTheme.colorScheme.colorBlue,
    ) {
        Icon(painter = painterResource(id = R.drawable.ic_add), contentDescription = null)
    }
}
