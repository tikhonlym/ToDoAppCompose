package com.todo.app.todoappcompose.app.theme

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
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