package com.todo.featureedit.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.todo.core.R
import com.todo.core.util.formatToString
import com.todo.core.util.millisecondsToLocalDate
import com.todo.core.theme.AppTheme
import java.time.LocalDate

@Composable
fun DeadlineUiItem(
    onDeadlineDate: (date: LocalDate?) -> Unit,
    modifier: Modifier = Modifier,
    deadlineDate: LocalDate?,
) {
    var dateDialogController by remember { mutableStateOf(false) }

    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = Modifier.clickable(
                interactionSource = null,
                indication = null
            ) {
                if (deadlineDate != null)
                    dateDialogController = true
            },
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = stringResource(R.string.deadline_title),
                color = AppTheme.colorScheme.labelPrimary,
                style = AppTheme.typographyScheme.body
            )
            AnimatedVisibility(visible = deadlineDate != null) {
                deadlineDate?.let {
                    Text(
                        text = it.formatToString(),
                        color = AppTheme.colorScheme.colorBlue,
                        style = AppTheme.typographyScheme.subhead
                    )
                }
            }
        }
        Switch(
            checked = deadlineDate != null,
            onCheckedChange = {
                if (!it) {
                    onDeadlineDate(null)
                }
                if (it) {
                    dateDialogController = true
                }
            },
            colors = SwitchDefaults.colors(
                checkedThumbColor = AppTheme.colorScheme.colorBlue,
                checkedTrackColor = AppTheme.colorScheme.colorLightBlue,
                checkedBorderColor = Color.Transparent,
                uncheckedThumbColor = AppTheme.colorScheme.backElevated,
                uncheckedTrackColor = AppTheme.colorScheme.supportOverlay,
                uncheckedBorderColor = Color.Transparent,
            )
        )
    }

    if (dateDialogController) {
        MyDatePickerDialog(
            onChangeController = { state ->
                dateDialogController = state
            },
            onChangeDeadlineDate = { date ->
                onDeadlineDate(date)
            },
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun DeadlineUiItemPreview() {
    Column {
        AppTheme {
            Column {
                DeadlineUiItem(
                    {}, deadlineDate = null
                )
                DeadlineUiItem(
                    {}, deadlineDate = millisecondsToLocalDate(0)
                )
            }
        }
    }
}