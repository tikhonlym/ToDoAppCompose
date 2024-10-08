package com.todo.featurehome.items

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
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.todo.core.R
import com.todo.core.theme.AppTheme

@Composable
fun CreateTaskUiListItem(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    var onClickEnabled by remember { mutableStateOf(true) }
    val description = stringResource(id = R.string.add_new_task)
    Box(
        modifier
            .fillMaxWidth()
            .background(AppTheme.colorScheme.backSecondary)
            .clickable(enabled = onClickEnabled) {
                onClick.invoke()
                onClickEnabled = false
            }
            .semantics {
                contentDescription = description
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