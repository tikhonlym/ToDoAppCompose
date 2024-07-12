package com.todo.featureedit.components

import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.todo.core.R
import com.todo.core.millisecondsToLocalDate
import com.todo.core.theme.AppTheme
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyDatePickerDialog(
    onChangeController: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    onChangeDeadlineDate: (LocalDate) -> Unit,
) {
    val datePickerState = rememberDatePickerState()
    DatePickerDialog(
        modifier = modifier,
        colors = DatePickerDefaults.colors(
            containerColor = AppTheme.colorScheme.backSecondary,
        ),
        onDismissRequest = { onChangeController(false) },
        confirmButton = {
            TextButton(onClick = {
                onChangeController(false)
                val date = datePickerState.selectedDateMillis
                if (date != null) {
                    onChangeDeadlineDate(millisecondsToLocalDate(date))
                }
            }) {
                Text(
                    text = stringResource(R.string.done_txt),
                    color = AppTheme.colorScheme.colorBlue,
                    style = AppTheme.typographyScheme.button
                )
            }
        },
        dismissButton = {
            TextButton(onClick = {
                onChangeController(false)
            }) {
                Text(
                    text = stringResource(R.string.cancel_txt),
                    color = AppTheme.colorScheme.colorBlue,
                    style = AppTheme.typographyScheme.button
                )
            }
        }
    ) {
        DatePicker(
            state = datePickerState,
            colors = DatePickerDefaults.colors(
                todayContentColor = AppTheme.colorScheme.colorBlue,
                todayDateBorderColor = AppTheme.colorScheme.colorBlue,
                dayContentColor = AppTheme.colorScheme.labelPrimary,
                selectedDayContentColor = AppTheme.colorScheme.colorWhite,
                selectedDayContainerColor = AppTheme.colorScheme.colorBlue,
                currentYearContentColor = AppTheme.colorScheme.labelPrimary,
                dayInSelectionRangeContentColor = AppTheme.colorScheme.labelPrimary,
                titleContentColor = AppTheme.colorScheme.labelPrimary,
                weekdayContentColor = AppTheme.colorScheme.labelTertiary,
                subheadContentColor = AppTheme.colorScheme.labelPrimary,
                headlineContentColor = AppTheme.colorScheme.labelPrimary,
                disabledDayContentColor = AppTheme.colorScheme.labelPrimary,
                containerColor = AppTheme.colorScheme.labelPrimary,
                yearContentColor = AppTheme.colorScheme.labelPrimary,
                selectedYearContentColor = AppTheme.colorScheme.labelPrimary,
                selectedYearContainerColor = AppTheme.colorScheme.labelPrimary,
                disabledSelectedDayContentColor = AppTheme.colorScheme.labelPrimary,
                dayInSelectionRangeContainerColor = AppTheme.colorScheme.labelPrimary,
                disabledSelectedDayContainerColor = AppTheme.colorScheme.labelPrimary,
            )
        )
    }
}

@Preview
@Composable
private fun MyDatePickerDialogPreview() {
    AppTheme {
        MyDatePickerDialog({}, Modifier, {})
    }
}

@Preview
@Composable
private fun MyDatePickerDialogPreviewDark() {
    AppTheme(darkTheme = true) {
        MyDatePickerDialog({}, Modifier, {})
    }
}