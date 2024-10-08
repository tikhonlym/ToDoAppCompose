package com.todo.featurehome.items

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import com.todo.core.R
import com.todo.core.theme.AppTheme

@Composable
fun SwitchVisibilityButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    checked: Boolean,
) {
    val description =
        if (checked)
            stringResource(id = R.string.close_completed_tasks)
        else
            stringResource(id = R.string.show_all_tasks)
    Crossfade(
        targetState = checked,
        animationSpec = tween(450)
    ) { targetState ->
        Icon(
            modifier = Modifier
                .clickable(interactionSource = null, indication = null) {
                    onClick.invoke()
                }
                .semantics {
                    contentDescription = description
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
private fun SwitchVisibilityButtonPreview() {
    AppTheme {
        Row {
            SwitchVisibilityButton({}, checked = false)
            SwitchVisibilityButton({}, checked = true)
        }
    }
}