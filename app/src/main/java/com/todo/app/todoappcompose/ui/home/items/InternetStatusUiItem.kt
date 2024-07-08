package com.todo.app.todoappcompose.ui.home.items

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.todo.app.todoappcompose.R
import com.todo.app.todoappcompose.app.theme.AppTheme

@Composable
fun InternetStatusUiItem(
    modifier: Modifier = Modifier,
    internetWorking: Boolean,
) {
    if(!internetWorking) {
        Box(
            modifier = modifier
                .background(
                    shape = RoundedCornerShape(8.dp),
                    color = AppTheme.colorScheme.backPrimary
                )
                .border(
                    BorderStroke(
                        1.dp, AppTheme.colorScheme.colorGray
                    ), // Цвет и толщина границы
                    shape = RoundedCornerShape(8.dp)
                )
                .padding(start = 4.dp, end = 4.dp)
        ) {
            Text(
                modifier = Modifier.align(Alignment.Center),
                text = stringResource(id = R.string.txt_offline),
                color = AppTheme.colorScheme.colorGray,
                style = AppTheme.typographyScheme.subhead
            )
        }
    }

    AnimatedVisibility(visible = internetWorking) {
        Box(
            modifier = Modifier
                .size(6.dp)
                .background(
                    shape = RoundedCornerShape(8.dp),
                    color = AppTheme.colorScheme.colorGreen
                )
        )
    }
}

@Preview
@Composable
private fun InternetStatusUiItemPrev() {
    AppTheme {
        InternetStatusUiItem(internetWorking = true)
    }
}