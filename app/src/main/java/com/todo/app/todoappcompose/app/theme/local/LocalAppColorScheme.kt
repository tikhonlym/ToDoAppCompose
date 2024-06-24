package com.todo.app.todoappcompose.app.theme.local

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

data class AppColorScheme(
    val supportSeparator: Color,
    val supportOverlay: Color,
    val labelPrimary: Color,
    val labelSecondary: Color,
    val labelTertiary: Color,
    val labelDisable: Color,
    val colorRed: Color,
    val colorGreen: Color,
    val colorBlue: Color,
    val colorLightBlue: Color,
    val colorGray: Color,
    val colorGrayLight: Color,
    val colorWhite: Color,
    val backPrimary: Color,
    val backSecondary: Color,
    val backElevated: Color,
)

val LocalAppColorScheme = staticCompositionLocalOf {
    AppColorScheme(
        supportSeparator = Color.Unspecified,
        supportOverlay = Color.Unspecified,
        labelPrimary = Color.Unspecified,
        labelSecondary = Color.Unspecified,
        labelTertiary = Color.Unspecified,
        labelDisable = Color.Unspecified,
        colorRed = Color.Unspecified,
        colorGreen = Color.Unspecified,
        colorBlue = Color.Unspecified,
        colorLightBlue = Color.Unspecified,
        colorGray = Color.Unspecified,
        colorGrayLight = Color.Unspecified,
        colorWhite = Color.Unspecified,
        backPrimary = Color.Unspecified,
        backSecondary = Color.Unspecified,
        backElevated = Color.Unspecified
    )
}