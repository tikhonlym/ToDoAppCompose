package com.todo.core.theme

import androidx.compose.ui.graphics.Color
import com.todo.core.theme.local.AppColorScheme

// Light theme colors -
val SupportSeparator = Color(0x33000000)
val SupportOverlay = Color(0x0F000000)
val LabelPrimary = Color(0xFF000000)
val LabelSecondary = Color(0x99000000)
val LabelTertiary = Color(0x4D000000)
val LabelDisable = Color(0x26000000)
val ColorRed = Color(0xFFFF3B30)
val ColorOrange = Color(0xFFFFA500)
val ColorGreen = Color(0xFF34C759)
val ColorBlue = Color(0xFF007AFF)
val ColorLightBlue = Color(0x4D007AFF)
val ColorGray = Color(0xFF8E8E93)
val ColorGrayLight = Color(0xFFD1D1D6)
val ColorWhite = Color(0xFFFFFFFF)
val BackPrimary = Color(0xFFF7F6F2)
val BackSecondary = Color(0xFFFFFFFF)
val BackElevated = Color(0xFFFFFFFF)

// Dark theme colors -
val SupportSeparatorDark = Color(0x33FFFFFF)
val SupportOverlayDark = Color(0x52000000)
val LabelPrimaryDark = Color(0xFFFFFFFF)
val LabelSecondaryDark = Color(0x99FFFFFF)
val LabelTertiaryDark = Color(0x66FFFFFF)
val LabelDisableDark = Color(0x26FFFFFF)
val ColorRedDark = Color(0xFFFF453A)
val ColorOrangeDark = Color(0xFFFFB841)
val ColorGreenDark = Color(0xFF32D74B)
val ColorBlueDark = Color(0xFF0A84FF)
val ColorLightBlueDark = Color(0x4D0A84FF)
val ColorGrayDark = Color(0xFF8E8E93)
val ColorGrayLightDark = Color(0xFF48484A)
val ColorWhiteDark = Color(0xFFFFFFFF)
val BackPrimaryDark = Color(0xFF161618)
val BackSecondaryDark = Color(0xFF252528)
val BackElevatedDark = Color(0xFF3C3C3F)

val darkColorScheme = AppColorScheme(
    supportSeparator = SupportSeparatorDark,
    supportOverlay = SupportOverlayDark,
    labelPrimary = LabelPrimaryDark,
    labelSecondary = LabelSecondaryDark,
    labelTertiary = LabelTertiaryDark,
    labelDisable = LabelDisableDark,
    colorRed = ColorRedDark,
    colorOrange = ColorOrangeDark,
    colorGreen = ColorGreenDark,
    colorBlue = ColorBlueDark,
    colorLightBlue = ColorLightBlueDark,
    colorGray = ColorGrayDark,
    colorGrayLight = ColorGrayLightDark,
    colorWhite = ColorWhiteDark,
    backPrimary = BackPrimaryDark,
    backSecondary = BackSecondaryDark,
    backElevated = BackElevatedDark,
)

val lightColorScheme = AppColorScheme(
    supportSeparator = SupportSeparator,
    supportOverlay = SupportOverlay,
    labelPrimary = LabelPrimary,
    labelSecondary = LabelSecondary,
    labelTertiary = LabelTertiary,
    labelDisable = LabelDisable,
    colorRed = ColorRed,
    colorOrange = ColorOrange,
    colorGreen = ColorGreen,
    colorBlue = ColorBlue,
    colorLightBlue = ColorLightBlue,
    colorGray = ColorGray,
    colorGrayLight = ColorGrayLight,
    colorWhite = ColorWhite,
    backPrimary = BackPrimary,
    backSecondary = BackSecondary,
    backElevated = BackElevated,
)