package com.todo.featureedit.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.todo.core.R
import com.todo.core.theme.AppTheme
import com.todo.domain.model.TaskImportance

@Composable
fun ChangeImportanceUiItem(
    modifier: Modifier = Modifier,
    importance: TaskImportance? = null,
) {
    Column(
        modifier = modifier
    ) {
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
fun BottomSheetScaffoldContent(
    onChangeImportance: (TaskImportance) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            modifier = Modifier
                .clickable {
                    onChangeImportance(TaskImportance.basic)
                }
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            text = stringResource(R.string.no_category_title),
            color = AppTheme.colorScheme.labelPrimary,
            style = AppTheme.typographyScheme.body
        )
        HorizontalDivider(
            modifier = Modifier.shadow(elevation = 1.dp),
            color = AppTheme.colorScheme.supportSeparator
        )
        Text(
            modifier = Modifier
                .clickable {
                    onChangeImportance(TaskImportance.low)
                }
                .fillMaxWidth()
                .padding(top = 16.dp, bottom = 16.dp),
            text = stringResource(R.string.low_category_title),
            color = AppTheme.colorScheme.labelPrimary,
            style = AppTheme.typographyScheme.body
        )
        HorizontalDivider(
            modifier = Modifier.shadow(elevation = 1.dp),
            color = AppTheme.colorScheme.supportSeparator
        )
        Text(
            modifier = Modifier
                .clickable {
                    onChangeImportance(TaskImportance.important)
                }
                .fillMaxWidth()
                .padding(top = 16.dp, bottom = 16.dp),
            text = stringResource(R.string.high_category_title),
            color = AppTheme.colorScheme.colorRed,
            style = AppTheme.typographyScheme.body
        )
    }
}

@Preview
@Composable
private fun ChangeImportanceUiItemPreview() {
    Column {
        AppTheme {
            ChangeImportanceUiItem()
        }
        AppTheme(darkTheme = true) {
            ChangeImportanceUiItem()
        }
    }
}