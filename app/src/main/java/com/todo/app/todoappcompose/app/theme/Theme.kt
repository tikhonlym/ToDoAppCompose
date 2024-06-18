package com.todo.app.todoappcompose.app.theme

import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import com.todo.app.todoappcompose.app.theme.local.AppColorScheme
import com.todo.app.todoappcompose.app.theme.local.AppTypography
import com.todo.app.todoappcompose.app.theme.local.LocalAppColorScheme
import com.todo.app.todoappcompose.app.theme.local.LocalAppTypography

@Composable
fun ToDoAppComposeTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    val colorScheme = if (darkTheme) darkColorScheme else lightColorScheme
    val rippleIndication = rememberRipple()
    CompositionLocalProvider(
        LocalAppColorScheme provides colorScheme,
        LocalAppTypography provides typography,
        LocalIndication provides rippleIndication,
        content = content
    )
}

object ToDoAppComposeTheme {
    val colorScheme: AppColorScheme
        @Composable get() = LocalAppColorScheme.current
    val typography: AppTypography
        @Composable get() = LocalAppTypography.current
}

private val darkColorScheme = AppColorScheme(
    supportSeparator = SupportSeparatorDark,
    supportOverlay = SupportOverlayDark,
    labelPrimary = LabelPrimaryDark,
    labelSecondary = LabelSecondaryDark,
    labelTertiary = LabelTertiaryDark,
    labelDisable = LabelDisableDark,
    colorRed = ColorRedDark,
    colorGreen = ColorGreenDark,
    colorBlue = ColorBlueDark,
    colorGray = ColorGrayDark,
    colorGrayLight = ColorGrayLightDark,
    colorWhite = ColorWhiteDark,
    backPrimary = BackPrimaryDark,
    backSecondary = BackSecondaryDark,
    backElevated = BackElevatedDark,
)

private val lightColorScheme = AppColorScheme(
    supportSeparator = SupportSeparator,
    supportOverlay = SupportOverlay,
    labelPrimary = LabelPrimary,
    labelSecondary = LabelSecondary,
    labelTertiary = LabelTertiary,
    labelDisable = LabelDisable,
    colorRed = ColorRed,
    colorGreen = ColorGreen,
    colorBlue = ColorBlue,
    colorGray = ColorGray,
    colorGrayLight = ColorGrayLight,
    colorWhite = ColorWhite,
    backPrimary = BackPrimary,
    backSecondary = BackSecondary,
    backElevated = BackElevated,
)


