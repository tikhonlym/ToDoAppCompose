package com.todo.featurehome.items

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.todo.core.R
import com.todo.core.theme.AppTheme

@Composable
fun SettingsButton(
    onNavigateToSettingsScreen: () -> Unit,
    modifier: Modifier = Modifier,
) {
    var enabled by remember { mutableStateOf(true) }
    val description = stringResource(id = R.string.settings)
    Box(
        modifier = modifier
            .size(32.dp)
            .background(
                shape = RoundedCornerShape(20f),
                color = AppTheme.colorScheme.backSecondary
            )
            .clip(
                shape = RoundedCornerShape(20f),
            )
            .clickable {
                if (enabled) {
                    enabled = false
                    onNavigateToSettingsScreen()
                }
            }
            .semantics {
                contentDescription = description
            },
    ) {
        Icon(
            modifier = Modifier.align(Alignment.Center),
            painter = painterResource(id = R.drawable.ic_settings_24),
            contentDescription = null,
            tint = AppTheme.colorScheme.supportSeparator
        )
    }
}

@Preview
@Composable
private fun SettingsFloatingButtonPrev() {
    AppTheme {
        SettingsButton({})
    }
}