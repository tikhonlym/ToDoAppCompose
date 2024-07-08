package com.todo.app.todoappcompose.ui.home.items

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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

@Composable
fun CreateTaskUiListItem(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    var onClickEnabled by remember { mutableStateOf(true) }
    Box(
        modifier
            .fillMaxWidth()
            .background(AppTheme.colorScheme.backSecondary)
            .clickable(enabled = onClickEnabled) {
                onClick.invoke()
                onClickEnabled = false
            }
            .padding(
                start = 16.dp,
                end = 16.dp,
                top = 12.dp,
                bottom = 12.dp
            ),
    ) {
        Text(
            modifier = Modifier
                .padding(start = 36.dp, top = 2.dp),
            text = stringResource(id = R.string.new_title),
            color = AppTheme.colorScheme.labelTertiary,
            style = AppTheme.typographyScheme.body,
        )
    }
}

@Preview
@Composable
private fun CreateTaskUiListItemPreview() {
    Column {
        AppTheme {
            CreateTaskUiListItem({})
        }
        AppTheme(darkTheme = true) {
            CreateTaskUiListItem({})
        }
    }
}