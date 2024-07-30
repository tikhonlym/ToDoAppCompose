package com.todo.featureedit

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import com.todo.core.R
import com.todo.core.theme.AppTheme
import com.todo.core.theme.component.CircleLoader
import com.todo.core.theme.component.StrokeStyle
import com.todo.core.theme.shimmerBackground
import com.todo.core.util.formatToMillis
import com.todo.core.util.generateUniqueIdForTask
import com.todo.core.util.getDeviceName
import com.todo.core.util.millisecondsToLocalDate
import com.todo.domain.model.TaskImportance
import com.todo.featureedit.components.BottomSheetScaffoldContent
import com.todo.featureedit.components.ChangeImportanceUiItem
import com.todo.featureedit.components.DeadlineUiItem
import com.todo.featureedit.components.DeleteTaskButton
import kotlinx.coroutines.launch
import java.time.LocalDate

@Composable
fun EditScreen(
    viewModel: EditViewModel = hiltViewModel(),
    id: String? = null,
    onNavigateUp: () -> Unit,
) {

    LaunchedEffect(key1 = Unit, block = {
        viewModel.initScreen(id = id)
        viewModel.onNavigateUp.collect { state ->
            if (state)
                onNavigateUp()
        }
    })

    val uiState = viewModel.uiState.collectAsState()

    when (uiState.value) {
        EditScreenState.CreatingNew -> {
            EditScreenComponent(
                onNavigateBack = onNavigateUp,
                viewModel = viewModel,
            )
        }

        EditScreenState.Editing -> {
            EditScreenComponent(
                onNavigateBack = onNavigateUp,
                viewModel = viewModel
            )
        }

        EditScreenState.Entering -> EditScreenLoadingMode()
    }
}

@Composable
fun EditScreenLoadingMode() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AppTheme.colorScheme.backPrimary),
    ) {
        EditToolBar({}, {})
        HorizontalDivider(
            modifier = Modifier.shadow(elevation = 4.dp),
            color = AppTheme.colorScheme.supportSeparator
        )
        Column(
            Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(modifier = Modifier.height(8.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .defaultMinSize(minHeight = 100.dp)
                    .shimmerBackground(RoundedCornerShape(8.dp)),
            )

            Spacer(modifier = Modifier.height(12.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp, bottom = 16.dp)
                    .defaultMinSize(minHeight = 40.dp)
                    .shimmerBackground(RoundedCornerShape(8.dp))
            )

            HorizontalDivider(color = AppTheme.colorScheme.supportSeparator)

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp, bottom = 16.dp)
                    .defaultMinSize(minHeight = 40.dp)
                    .shimmerBackground(RoundedCornerShape(8.dp))
            )

            HorizontalDivider(color = AppTheme.colorScheme.supportSeparator)

            DeleteTaskButton(
                modifier = Modifier.padding(top = 12.dp, bottom = 12.dp),
                onClick = {},
                active = false
            )

            Spacer(modifier = Modifier.height(48.dp))
        }
    }
}

@Preview
@Composable
private fun EditScreenLoadingModePrev() {
    AppTheme {
        EditScreenLoadingMode()
    }
}

@OptIn(
    ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class,
)
@Composable
fun EditScreenComponent(
    onNavigateBack: () -> Unit,
    viewModel: EditViewModel = hiltViewModel(),
) {
    val todoTask = viewModel.currentTask.collectAsState()
    val isCircleLoaderActive = viewModel.circleLoaderActive.collectAsState()

    var textFieldValue by remember { mutableStateOf(TextFieldValue(todoTask.value.text)) }
    val todoText = textFieldValue.text.ifEmpty { stringResource(id = R.string.no_description) }

    val deadlineDate: MutableState<Long?> = remember { mutableStateOf(todoTask.value.deadline) }

    val scope = rememberCoroutineScope()

    val bottomSheetSkipState = remember { mutableStateOf(false) }
    val scaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = rememberStandardBottomSheetState(
            initialValue = SheetValue.Hidden,
            skipHiddenState = bottomSheetSkipState.value
        )
    )
    val scaffoldBlackLoaderState = remember { mutableStateOf(false) }

    var importanceState by remember {
        mutableStateOf(todoTask.value.importance)
    }

    val isAnimatingImportanceIndication = remember { mutableStateOf(false) }
    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        sheetContent = {
            BottomSheetScaffoldContent(
                onChangeImportance = { importance ->
                    importanceState = importance
                    scope.launch {
                        bottomSheetSkipState.value = true
                        isAnimatingImportanceIndication.value = true
                        scaffoldBlackLoaderState.value = false
                        scaffoldState.bottomSheetState.hide()
                    }
                }
            )
        },
        sheetSwipeEnabled = false,
        sheetPeekHeight = 0.dp
    ) {

        if (scaffoldBlackLoaderState.value) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.5f))
                    .clickable {
                        scope.launch {
                            bottomSheetSkipState.value = true
                            scaffoldState.bottomSheetState.hide()
                        }
                        scaffoldBlackLoaderState.value = false
                    }
                    .zIndex(2f)
            )
        }

        Box(
            Modifier
                .pointerInteropFilter {
                    isCircleLoaderActive.value
                }
                .fillMaxSize()
        ) {
            CircleLoader(
                color = AppTheme.colorScheme.colorBlue,
                secondColor = null,
                tailLength = 280f,
                modifier = Modifier
                    .size(50.dp)
                    .align(Alignment.Center)
                    .zIndex(2f),
                isVisible = isCircleLoaderActive.value,
                strokeStyle = StrokeStyle(width = 8.dp)
            )
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(AppTheme.colorScheme.backPrimary),
            ) {
                EditToolBar(
                    onClose = {
                        onNavigateBack()
                    },
                    onSaveTask = {
                        viewModel.createOrUpdateTask(
                            if (viewModel.uiState.value == EditScreenState.CreatingNew) {
                                todoTask.value.copy(
                                    id = generateUniqueIdForTask(),
                                    text = todoText,
                                    importance = importanceState,
                                    deadline = deadlineDate.value,
                                    done = false,
                                    created_at = formatToMillis(LocalDate.now()),
                                    changed_at = formatToMillis(LocalDate.now()),
                                    last_updated_by = getDeviceName()
                                )
                            } else {
                                todoTask.value.copy(
                                    text = todoText,
                                    importance = importanceState,
                                    deadline = deadlineDate.value,
                                    changed_at = formatToMillis(LocalDate.now()),
                                    last_updated_by = getDeviceName()
                                )
                            },
                            create = viewModel.uiState.value == EditScreenState.CreatingNew
                        )
                    }
                )

                HorizontalDivider(
                    modifier = Modifier.shadow(elevation = 4.dp),
                    color = AppTheme.colorScheme.supportSeparator
                )

                Column(
                    Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 16.dp)
                        .verticalScroll(rememberScrollState())
                ) {
                    Spacer(modifier = Modifier.height(8.dp))

                    val basicTextFieldBackgroundColor by animateColorAsState(
                        targetValue = if (importanceState == TaskImportance.important) {
                            if (isAnimatingImportanceIndication.value)
                                AppTheme.colorScheme.colorRed
                            else
                                AppTheme.colorScheme.backSecondary
                        } else {
                            AppTheme.colorScheme.backSecondary
                        },
                        animationSpec = tween(durationMillis = 500),
                        finishedListener = {
                            isAnimatingImportanceIndication.value = false
                        },
                        label = "basicTextFieldBackgroundColor"
                    )

                    BasicTextField(
                        modifier = Modifier
                            .defaultMinSize(minHeight = 100.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(AppTheme.colorScheme.backSecondary)
                            .border(
                                BorderStroke(1.dp, basicTextFieldBackgroundColor),
                                RoundedCornerShape(8.dp)
                            )
                            .testTag("taskDescriptionField"),
                        value = textFieldValue,
                        textStyle = AppTheme.typographyScheme.body
                            .copy(color = AppTheme.colorScheme.labelPrimary),
                        onValueChange = { textFieldValue = it },
                        decorationBox = { innerTextField ->
                            Row(
                                Modifier
                                    .padding(16.dp)
                                    .fillMaxWidth()
                            ) {
                                if (textFieldValue.text.isEmpty()) {
                                    Text(
                                        text = stringResource(R.string.hint_text_your),
                                        style = AppTheme.typographyScheme.body,
                                        color = AppTheme.colorScheme.supportSeparator
                                    )
                                }
                                innerTextField()
                            }
                        },
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    val description = stringResource(id = R.string.change_importance)
                    ChangeImportanceUiItem(
                        modifier = Modifier
                            .padding(top = 16.dp, bottom = 16.dp)
                            .clickable {
                                scaffoldBlackLoaderState.value = true
                                scope.launch {
                                    scaffoldState.bottomSheetState.expand()
                                }
                            }
                            .semantics {
                                contentDescription = description
                            },
                        importance = importanceState
                    )

                    HorizontalDivider(color = AppTheme.colorScheme.supportSeparator)

                    DeadlineUiItem(
                        onDeadlineDate = { date ->
                            deadlineDate.value = if (date != null) formatToMillis(date) else null
                        },
                        modifier = Modifier.padding(top = 16.dp, bottom = 16.dp),
                        deadlineDate = if (deadlineDate.value != null) millisecondsToLocalDate(
                            deadlineDate.value!!
                        ) else null
                    )

                    HorizontalDivider(color = AppTheme.colorScheme.supportSeparator)

                    val onDeleteButtonActive = viewModel.deleteButtonActive.collectAsState()
                    DeleteTaskButton(
                        modifier = Modifier.padding(top = 12.dp, bottom = 12.dp),
                        onClick = {
                            viewModel.deleteTask(todoTask.value.id)
                        },
                        active = onDeleteButtonActive.value
                    )

                    Spacer(modifier = Modifier.height(48.dp))
                }
            }
        }
    }
}

@Composable
private fun EditToolBar(
    onClose: () -> Unit,
    onSaveTask: () -> Unit,
    modifier: Modifier = Modifier,
) {
    var onClickEnabled by remember { mutableStateOf(true) }
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(AppTheme.colorScheme.backPrimary)
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        val description = stringResource(id = R.string.close_screen)
        Icon(
            modifier = Modifier
                .size(24.dp)
                .clickable(
                    enabled = onClickEnabled,
                    interactionSource = null,
                    indication = null
                ) {
                    onClose.invoke()
                    onClickEnabled = false
                }
                .semantics {
                    contentDescription = description
                },
            tint = AppTheme.colorScheme.labelPrimary,
            painter = painterResource(R.drawable.ic_close),
            contentDescription = null,
        )
        val contentDesc = stringResource(id = R.string.save_task_text)
        Text(
            modifier = Modifier
                .clickable(
                    enabled = onClickEnabled,
                    interactionSource = null,
                    indication = null
                ) {
                    onSaveTask.invoke()
                    onClickEnabled = false
                }
                .semantics {
                    contentDescription = contentDesc
                }
                .testTag("saveButton"),
            text = stringResource(R.string.btn_save_title),
            color = AppTheme.colorScheme.colorBlue,
            style = AppTheme.typographyScheme.button
        )
    }
}