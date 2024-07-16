package com.todo.core.theme.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.todo.core.theme.AppTheme


data class SnackBarData(
    val text: String,
    val onClick: () -> Unit = {},
    val buttonText: String? = null,
)

@Composable
fun CustomSnackBar(
    modifier: Modifier = Modifier,
    data: SnackBarData,
) {
    Snackbar(
        action = {
            if (data.buttonText != null) {
                Button(
                    onClick = {
                        data.onClick()
                    }
                ) {
                    Text(
                        text = data.buttonText,
                        style = AppTheme.typographyScheme.button,
                        color = Color.White
                    )
                }
            }
        },
        modifier = modifier
            .padding(8.dp)
            .clip(RoundedCornerShape(32.dp)),
        containerColor = AppTheme.colorScheme.labelSecondary,
        contentColor = AppTheme.colorScheme.labelSecondary
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = data.text,
                style = AppTheme.typographyScheme.body,
                color = AppTheme.colorScheme.backPrimary
            )
        }
    }
}