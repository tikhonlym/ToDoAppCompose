package com.todo.app.todoappcompose.ui.home.items

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.todo.app.todoappcompose.R
import com.todo.app.todoappcompose.app.theme.AppTheme

@Composable
fun SyncButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    Box(
        modifier
            .size(40.dp)
            .clip(CircleShape)
            .background(AppTheme.colorScheme.backSecondary)
            .border(
                BorderStroke(
                    1.dp, AppTheme.colorScheme.colorOrange
                ),
                shape = CircleShape
            )
            .clickable { onClick() }
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_sync),
            contentDescription = "Sync",
            tint = AppTheme.colorScheme.colorOrange,
            modifier = Modifier
                .padding(top = 4.dp)
                .size(24.dp)
                .align(Alignment.Center)
        )
    }
}

@Preview
@Composable
private fun SyncButtonPrev() {
    AppTheme {
        SyncButton(
            onClick = {}
        )
    }
}