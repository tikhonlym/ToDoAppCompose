package com.todo.featurehome.items

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.todo.core.R
import com.todo.core.theme.AppTheme

@Composable
fun NewTaskFloatingButton(
    onNewTask: () -> Unit,
    modifier: Modifier = Modifier,
) {
    var enabled by remember { mutableStateOf(true) }
    FloatingActionButton(
        shape = CircleShape,
        modifier = modifier
            .size(56.dp)
            .testTag("addTaskButton"),
        onClick = {
            if (enabled) {
                enabled = false
                onNewTask()
            }
        },
        contentColor = AppTheme.colorScheme.colorWhite,
        containerColor = AppTheme.colorScheme.colorBlue,
    ) {
        Icon(painter = painterResource(id = R.drawable.ic_add), contentDescription = null)
    }
}

@Preview
@Composable
private fun NewTaskFloatingButtonPreview() {
    AppTheme {
        NewTaskFloatingButton({})
    }
}