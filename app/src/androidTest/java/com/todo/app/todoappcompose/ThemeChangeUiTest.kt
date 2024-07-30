package com.todo.app.todoappcompose

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.todo.core.config.theme.AppThemeMode
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ThemeChangeUiTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule(MainActivity::class.java)

    @Test
    fun testChangeThemeToALWAYS_DARK() {

        // Запускаем UI для тестирования
        composeTestRule.waitForIdle()

        // Шаг 2: Перейти в настройки
        composeTestRule.waitUntil(timeoutMillis = 3000) {
            // Здесь ждем, пока настройки отрисуются
            composeTestRule.onAllNodesWithTag("settings").fetchSemanticsNodes().isNotEmpty()
        }
        composeTestRule.onNodeWithTag("settings").performClick()

        // Шаг 3: Проверяем, что открылась форма для изменеия темы
        composeTestRule.waitUntil(timeoutMillis = 3000) {
            composeTestRule.onAllNodesWithTag("themeChangeWindow").fetchSemanticsNodes()
                .isNotEmpty()
        }
        composeTestRule.onNodeWithTag("themeChangeWindow").assertIsDisplayed()

        // Шаг 4: Изменить тему на ALWAYS_DARK
        composeTestRule.onNodeWithTag("alwaysDark").performClick()

        // Шаг 5: Проверить, что тема поменялась
        val themeMode = composeTestRule.activity.themeConfig.appThemeMode.value
        assert(
            composeTestRule.activity.themeConfig.appThemeMode.value == AppThemeMode.ALWAYS_DARK
        ) {
            "Expected app theme to be ALWAYS_DARK but was $themeMode"
        }
    }

    @Test
    fun testChangeThemeToALWAYS_LIGHT() {

        // Запускаем UI для тестирования
        composeTestRule.waitForIdle()

        // Шаг 2: Перейти в настройки
        composeTestRule.waitUntil(timeoutMillis = 3000) {
            // Здесь ждем, пока настройки отрисуются
            composeTestRule.onAllNodesWithTag("settings").fetchSemanticsNodes().isNotEmpty()
        }
        composeTestRule.onNodeWithTag("settings").performClick()

        // Шаг 3: Проверяем, что открылась форма для изменеия темы
        composeTestRule.waitUntil(timeoutMillis = 3000) {
            composeTestRule.onAllNodesWithTag("themeChangeWindow").fetchSemanticsNodes()
                .isNotEmpty()
        }
        composeTestRule.onNodeWithTag("themeChangeWindow").assertIsDisplayed()

        // Шаг 4: Изменить тему на ALWAYS_LIGHT
        composeTestRule.onNodeWithTag("alwaysLight").performClick()

        // Шаг 5: Проверить, что тема поменялась
        val themeMode = composeTestRule.activity.themeConfig.appThemeMode.value
        assert(
            composeTestRule.activity.themeConfig.appThemeMode.value == AppThemeMode.ALWAYS_LIGHT
        ) {
            "Expected app theme to be ALWAYS_LIGHT but was $themeMode"
        }
    }

    @Test
    fun testChangeThemeToSYSTEM() {

        // Запускаем UI для тестирования
        composeTestRule.waitForIdle()

        // Шаг 2: Перейти в настройки
        composeTestRule.waitUntil(timeoutMillis = 3000) {
            // Здесь ждем, пока настройки отрисуются
            composeTestRule.onAllNodesWithTag("settings").fetchSemanticsNodes().isNotEmpty()
        }
        composeTestRule.onNodeWithTag("settings").performClick()

        // Шаг 3: Проверяем, что открылась форма для изменеия темы
        composeTestRule.waitUntil(timeoutMillis = 3000) {
            composeTestRule.onAllNodesWithTag("themeChangeWindow").fetchSemanticsNodes()
                .isNotEmpty()
        }
        composeTestRule.onNodeWithTag("themeChangeWindow").assertIsDisplayed()

        // Шаг 4: Изменить тему на SYSTEM
        composeTestRule.onNodeWithTag("system").performClick()

        // Шаг 5: Проверить, что тема поменялась
        val themeMode = composeTestRule.activity.themeConfig.appThemeMode.value
        assert(
            composeTestRule.activity.themeConfig.appThemeMode.value == AppThemeMode.SYSTEM
        ) {
            "Expected app theme to be SYSTEM but was $themeMode"
        }
    }
}
