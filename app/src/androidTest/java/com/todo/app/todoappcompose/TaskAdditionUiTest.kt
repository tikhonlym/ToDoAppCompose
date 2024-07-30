package com.todo.app.todoappcompose

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollToNode
import androidx.compose.ui.test.performTextInput
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.UUID

@RunWith(AndroidJUnit4::class)
class TaskAdditionUiTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule(MainActivity::class.java)

    @Test
    fun testAddTask() {
        val description = UUID.randomUUID().toString() +
                "Your test tag"

        // Запускаем UI для тестирования
        composeTestRule.waitForIdle()

        // Шаг 2: Нажать на «+»
        composeTestRule.waitUntil(timeoutMillis = 3000) {
            // Здесь ждем, пока элемент с тегом "addTaskButton" станет видимым
            composeTestRule.onAllNodesWithTag("addTaskButton").fetchSemanticsNodes().isNotEmpty()
        }
        composeTestRule.onNodeWithTag("addTaskButton").performClick()

        // Шаг 3: Проверяем, что открылась форма для ввода задачи
        composeTestRule.onNodeWithTag("taskDescriptionField").assertIsDisplayed()
        composeTestRule.onNodeWithTag("saveButton").assertIsDisplayed()

        // Шаг 4: Ввести описание задачи
        composeTestRule.onNodeWithTag("taskDescriptionField")
            .performTextInput(description)

        // Шаг 5: Нажать «Сохранить»
        composeTestRule.onNodeWithTag("saveButton").performClick()
        composeTestRule.waitUntil(timeoutMillis = 3000) {
            // Здесь ждем, пока элемент с тегом "taskList" станет видимым
            composeTestRule.onAllNodesWithTag("taskList").fetchSemanticsNodes().isNotEmpty()
        }

        // Шаг 6: Прокручиваем список вниз
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithTag("taskList")
            .performScrollToNode(hasText(description))

        // Шаг 7: Проверяем, что задача появилась в конце списка
        composeTestRule.onNodeWithTag("taskList")
            .assertIsDisplayed()
    }
}