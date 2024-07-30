package com.todo.featuresettings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.todo.core.R
import com.todo.core.config.theme.AppThemeMode
import com.todo.core.theme.AppTheme
import com.todo.featuresettings.components.AboutUiItem
import com.todo.featuresettings.components.SettingsToolBar

@Composable
fun SettingsScreen(
    onNavigateUp: () -> Unit,
    onNavigateToAbout: () -> Unit,
    viewModel: SettingsViewModel = hiltViewModel(),
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AppTheme.colorScheme.backPrimary)
    ) {
        SettingsToolBar(onNavigateUp)

        HorizontalDivider(
            modifier = Modifier.shadow(elevation = 4.dp),
            color = AppTheme.colorScheme.supportSeparator
        )

        Column(
            modifier = Modifier
                .padding(top = 8.dp, start = 16.dp, end = 16.dp)
                .background(
                    shape = RoundedCornerShape(8.dp),
                    color = AppTheme.colorScheme.backSecondary
                )
                .padding(top = 16.dp, bottom = 8.dp, start = 16.dp)
        ) {
            Text(
                text = stringResource(id = R.string.app_theme_title),
                color = AppTheme.colorScheme.labelPrimary,
                style = AppTheme.typographyScheme.title,
            )

            Spacer(modifier = Modifier.height(16.dp))

            val selectedOption = remember {
                mutableStateOf(viewModel.getThemeMode())
            }

            Column(modifier = Modifier
                .fillMaxWidth()
                .testTag("themeChangeWindow")) {
                ThemeSelectionUIItem(
                    onChangeTheme = {
                        viewModel.changeThemeMode(AppThemeMode.SYSTEM)
                        selectedOption.value = viewModel.getThemeMode()
                    },
                    text = stringResource(id = R.string.like_in_system),
                    checked = selectedOption.value == AppThemeMode.SYSTEM,
                    modifierForCheckBox = Modifier.testTag("system")
                )
                ThemeSelectionUIItem(
                    onChangeTheme = {
                        viewModel.changeThemeMode(AppThemeMode.ALWAYS_DARK)
                        selectedOption.value = viewModel.getThemeMode()
                    },
                    text = stringResource(id = R.string.always_dark),
                    checked = selectedOption.value == AppThemeMode.ALWAYS_DARK,
                    modifierForCheckBox = Modifier.testTag("alwaysDark")
                )
                ThemeSelectionUIItem(
                    onChangeTheme = {
                        viewModel.changeThemeMode(AppThemeMode.ALWAYS_LIGHT)
                        selectedOption.value = viewModel.getThemeMode()
                    },
                    text = stringResource(id = R.string.always_light),
                    checked = selectedOption.value == AppThemeMode.ALWAYS_LIGHT,
                    modifierForCheckBox = Modifier.testTag("alwaysLight")
                )
            }
        }

        Column(Modifier.padding(start = 16.dp, end = 16.dp)) {
            Spacer(modifier = Modifier.height(16.dp))

            HorizontalDivider(color = AppTheme.colorScheme.supportSeparator)

            AboutUiItem(
                onClick = {
                    onNavigateToAbout()
                },
            )

            HorizontalDivider(color = AppTheme.colorScheme.supportSeparator)
        }
    }
}

@Composable
private fun ThemeSelectionUIItem(
    onChangeTheme: () -> Unit,
    modifier: Modifier = Modifier,
    modifierForCheckBox: Modifier = Modifier,
    text: String,
    checked: Boolean,
) {
    Row(
        modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            modifier = modifierForCheckBox,
            checked = checked,
            onCheckedChange = {
                onChangeTheme.invoke()
            },
            colors = if (checked) CheckboxDefaults.colors(
                checkedColor = AppTheme.colorScheme.colorGreen,
                checkmarkColor = AppTheme.colorScheme.backSecondary,
            ) else {
                CheckboxDefaults.colors(
                    uncheckedColor = AppTheme.colorScheme.supportSeparator
                )
            }
        )
        Text(
            text = text,
            color = AppTheme.colorScheme.labelPrimary,
            style = AppTheme.typographyScheme.body
        )
    }
}

@Preview
@Composable
private fun SettingsScreenPreview() {
    AppTheme {
        SettingsScreen({}, {})
    }
}
