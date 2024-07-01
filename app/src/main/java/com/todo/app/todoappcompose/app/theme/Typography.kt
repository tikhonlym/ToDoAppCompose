package com.todo.app.todoappcompose.app.theme

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.todo.app.todoappcompose.app.theme.local.AppTypography

val typography = AppTypography(
    largeTitle = TextStyle(
        fontFamily = RobotoFonts,
        fontSize = 32.sp,
        lineHeight = 38.sp,
        fontWeight = FontWeight.Medium
    ),
    title = TextStyle(
        fontFamily = RobotoFonts,
        fontSize = 20.sp,
        lineHeight = 32.sp,
        letterSpacing = 0.5.sp,
        fontWeight = FontWeight.Medium
    ),
    button = TextStyle(
        fontFamily = RobotoFonts,
        fontSize = 14.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.16.sp,
        fontWeight = FontWeight.Medium
    ),
    body = TextStyle(
        fontFamily = RobotoFonts,
        fontSize = 16.sp,
        lineHeight = 20.sp,
        fontWeight = FontWeight.Normal
    ),
    subhead = TextStyle(
        fontFamily = RobotoFonts,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        fontWeight = FontWeight.Normal
    )
)

@Preview(showBackground = true)
@Composable
private fun TypographyPreview() {
    AppTheme {
        Column(Modifier.padding(32.dp)) {
            Text(text = "Large title — 32/38", style = AppTheme.typographyScheme.largeTitle)
            Spacer(modifier = Modifier.height(24.dp))
            Text(text = "Title — 20/32", style = AppTheme.typographyScheme.title)
            Spacer(modifier = Modifier.height(24.dp))
            Text(text = "BUTTON — 14/24", style = AppTheme.typographyScheme.button)
            Spacer(modifier = Modifier.height(24.dp))
            Text(text = "Body — 16/20", style = AppTheme.typographyScheme.body)
            Spacer(modifier = Modifier.height(24.dp))
            Text(text = "Subhead — 14/20", style = AppTheme.typographyScheme.subhead)
        }
    }
}