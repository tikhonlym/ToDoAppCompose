package com.todo.app.todoappcompose.presentation.home.view

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.todo.app.todoappcompose.R
import com.todo.app.todoappcompose.app.theme.AppTheme

@Composable
fun SwitchVisibilityTaskButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    checked: Boolean,
) {

    Crossfade(
        targetState = checked,
        animationSpec = tween(450)
    ) { targetState ->
        Icon(
            modifier = Modifier
                .clickable(interactionSource = null, indication = null) {
                    onClick.invoke()
                }
                .then(modifier),
            painter = painterResource(
                if (targetState) R.drawable.ic_visibility else R.drawable.ic_visibility_off
            ),
            tint = AppTheme.colorScheme.colorBlue,
            contentDescription = null
        )
    }
}

@Preview
@Composable
private fun SwitchVisibilityTaskButtonPrev() {
    AppTheme {
        Row {
            SwitchVisibilityTaskButton({}, checked = false)
            SwitchVisibilityTaskButton({}, checked = true)
        }
    }
}