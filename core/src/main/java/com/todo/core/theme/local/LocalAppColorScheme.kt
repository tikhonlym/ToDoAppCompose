package com.todo.core.theme.local

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.lightColorScheme
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
    val colorOrange: Color,
    val colorGreen: Color,
    val colorBlue: Color,
    val colorLightBlue: Color,
    val colorGray: Color,
    val colorGrayLight: Color,
    val colorWhite: Color,
    val backPrimary: Color,
    val backSecondary: Color,
    val backElevated: Color,
    val materialColors: ColorScheme
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
        colorOrange = Color.Unspecified,
        colorGreen = Color.Unspecified,
        colorBlue = Color.Unspecified,
        colorLightBlue = Color.Unspecified,
        colorGray = Color.Unspecified,
        colorGrayLight = Color.Unspecified,
        colorWhite = Color.Unspecified,
        backPrimary = Color.Unspecified,
        backSecondary = Color.Unspecified,
        backElevated = Color.Unspecified,
        materialColors = defaultMaterialColors
    )
}

val defaultMaterialColors = lightColorScheme(
    primary = Color.Unspecified,
    onPrimary = Color.Unspecified,
    primaryContainer = Color.Unspecified,
    onPrimaryContainer = Color.Unspecified,
    secondary = Color.Unspecified,
    onSecondary = Color.Unspecified,
    secondaryContainer = Color.Unspecified,
    onSecondaryContainer = Color.Unspecified,
    error = Color.Unspecified,
    onError = Color.Unspecified,
    background = Color.Unspecified,
    onBackground = Color.Unspecified,
    surface = Color.Unspecified,
    onSurface = Color.Unspecified
)