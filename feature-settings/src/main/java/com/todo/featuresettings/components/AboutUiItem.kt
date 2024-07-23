package com.todo.featuresettings.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.todo.core.theme.AppTheme

@Composable
fun AboutUiItem(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val clickable = remember {
        mutableStateOf(true)
    }
    Box(
        modifier = modifier
            .clickable(enabled = clickable.value) {
                onClick()
                clickable.value = false
            }
            .padding(top = 16.dp, bottom = 16.dp)
            .fillMaxWidth()

    ) {
        Text(
            modifier = Modifier.align(Alignment.CenterStart),
            text = "О приложении",
            color = AppTheme.colorScheme.labelPrimary,
            style = AppTheme.typographyScheme.body
        )
        Icon(
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .size(24.dp),
            painter = painterResource(id = com.todo.core.R.drawable.ic_info_outline),
            contentDescription = null,
            tint = AppTheme.colorScheme.labelTertiary
        )
    }
}

@Preview
@Composable
private fun AboutUiItemPreview() {
    AppTheme {
        AboutUiItem({})
    }
}