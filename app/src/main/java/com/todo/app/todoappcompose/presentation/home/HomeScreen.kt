package com.todo.app.todoappcompose.presentation.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import com.todo.app.todoappcompose.R
import com.todo.app.todoappcompose.app.theme.AppTheme
import com.todo.app.todoappcompose.app.dispatchers.DispatchersImpl
import com.todo.app.todoappcompose.data.repository.todo.TodoItemsRepositoryImpl
import com.todo.app.todoappcompose.domain.usecase.CompleteTaskUseCase
import com.todo.app.todoappcompose.domain.usecase.CountCompletedTaskUseCase
import com.todo.app.todoappcompose.domain.usecase.GetTaskListUseCase
import com.todo.app.todoappcompose.presentation.home.ui.CreateTaskUiListItem
import com.todo.app.todoappcompose.presentation.home.ui.TodoUiItem
import com.todo.app.todoappcompose.presentation.home.ui.NewTaskFloatingButton
import com.todo.app.todoappcompose.presentation.home.ui.SwitchVisibilityButton
import kotlinx.serialization.Serializable


@Serializable
object HomeScreenDestination

private val EXPANDED_TOP_BAR_HEIGHT = 85.dp
private val COLLAPSED_TOP_BAR_HEIGHT = 56.dp

@Composable
fun HomeScreen(
    onNavigateToEditScreen: (String?) -> Unit,
    viewModel: HomeViewModel = hiltViewModel(),
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(AppTheme.colorScheme.backPrimary)
    ) {
        HomeScreenComponent(onNavigateToEditScreen, viewModel)

        NewTaskFloatingButton(
            onNewTask = {
                onNavigateToEditScreen(null)
            },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(end = 16.dp, bottom = 24.dp)
        )
    }
}

@Composable
private fun HomeScreenComponent(
    onNavigateToEditScreen: (String?) -> Unit,
    viewModel: HomeViewModel,
) {
    val listState = rememberLazyListState()

    val overlapHeightPx = with(LocalDensity.current) {
        EXPANDED_TOP_BAR_HEIGHT.toPx() - COLLAPSED_TOP_BAR_HEIGHT.toPx()
    }

    val isCollapsed: Boolean by remember {
        derivedStateOf {
            val isFirstItemHidden = listState.firstVisibleItemScrollOffset * 0.48f > overlapHeightPx
            isFirstItemHidden || listState.firstVisibleItemIndex > 0
        }
    }

    viewModel.countCompletedTask()

    val todoList = viewModel.todoList.collectAsState()
    val showCompleted = viewModel.showCompletedTasks.collectAsState()
    val countCompletedTasks = viewModel.countCompleted.collectAsState()
    var onTaskClickEnabled by remember { mutableStateOf(true) }

    CollapsedTopBar(
        modifier = Modifier.zIndex(2f),
        isCollapsed = isCollapsed
    )
    LazyColumn(state = listState) {

        item {
            ExpandedToolbar(
                onSwitch = { show ->
                    viewModel.showCompletedTasks(show)
                },
                countCompleted = countCompletedTasks.value,
                show = showCompleted.value,
            )
        }

        items(count = todoList.value.size) { index ->
            val curItem = todoList.value[index]
            TodoUiItem(
                onCompleteClick = { id, isDone ->
                    viewModel.completeTask(id, isDone)
                },
                onItemClicked = { id ->
                    onNavigateToEditScreen(id)
                    onTaskClickEnabled = false
                },
                modifier = Modifier.padding(start = 8.dp, end = 8.dp),
                enabled = onTaskClickEnabled,
                showCompletedTasks = showCompleted.value,
                data = curItem,
            )
        }

        item {
            CreateTaskUiListItem(
                onClick = {
                    onNavigateToEditScreen(null)
                },
                modifier = Modifier.padding(start = 8.dp, end = 8.dp)
            )
        }

        item {
            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}

@Composable
private fun ExpandedToolbar(
    onSwitch: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    countCompleted: Int,
    show: Boolean,
) {
    var localShowState by remember { mutableStateOf(show) }
    Column(
        modifier = modifier
            .padding(start = 60.dp, top = 50.dp)
            .fillMaxWidth()
            .height(EXPANDED_TOP_BAR_HEIGHT)
    ) {
        Text(
            text = stringResource(id = R.string.my_tasks_title),
            style = AppTheme.typographyScheme.largeTitle,
            color = AppTheme.colorScheme.labelPrimary
        )
        Spacer(modifier = Modifier.height(4.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 24.dp),
            horizontalArrangement = Arrangement.Absolute.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(id = R.string.completed_title) + countCompleted,
                style = AppTheme.typographyScheme.body,
                color = AppTheme.colorScheme.labelTertiary
            )
            SwitchVisibilityButton(
                onClick = {
                    localShowState = !localShowState
                    onSwitch.invoke(localShowState)
                },
                checked = localShowState,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}

@Composable
private fun CollapsedTopBar(
    modifier: Modifier = Modifier,
    isCollapsed: Boolean,
) {
    val color: Color by animateColorAsState(
        if (isCollapsed) AppTheme.colorScheme.backPrimary else Color.Transparent
    )
    Box(
        modifier = modifier
            .background(color)
            .fillMaxWidth()
            .height(COLLAPSED_TOP_BAR_HEIGHT),
    ) {
        AnimatedVisibility(visible = isCollapsed) {
            Column(Modifier.fillMaxWidth()) {
                Text(
                    modifier = Modifier.padding(16.dp),
                    text = stringResource(id = R.string.my_tasks_title),
                    color = AppTheme.colorScheme.labelPrimary,
                    style = AppTheme.typographyScheme.title,
                )
                Divider(
                    modifier = Modifier.shadow(elevation = 4.dp),
                    color = AppTheme.colorScheme.supportSeparator
                )
            }
        }
    }
}

@Preview
@Composable
private fun CollapsedTopBarPreview() {
    AppTheme {
        CollapsedTopBar(isCollapsed = true)
    }
}

@Preview
@Composable
private fun HomeScreenPreview() {
    AppTheme {
        HomeScreen(
            {}, HomeViewModel(
                getTodoListUseCase = GetTaskListUseCase(
                    TodoItemsRepositoryImpl(dispatcher = DispatchersImpl())
                ),
                completeTodoTaskUseCase = CompleteTaskUseCase(
                    TodoItemsRepositoryImpl(dispatcher = DispatchersImpl())
                ),
                countCompletedTaskUseCase = CountCompletedTaskUseCase(
                    TodoItemsRepositoryImpl(dispatcher = DispatchersImpl())
                )
            )
        )
    }
}

@Preview
@Composable
private fun HomeScreenPreviewDark() {
    AppTheme(darkTheme = true) {
        HomeScreen(
            {}, HomeViewModel(
                getTodoListUseCase = GetTaskListUseCase(
                    TodoItemsRepositoryImpl(dispatcher = DispatchersImpl())
                ),
                completeTodoTaskUseCase = CompleteTaskUseCase(
                    TodoItemsRepositoryImpl(dispatcher = DispatchersImpl())
                ),
                countCompletedTaskUseCase = CountCompletedTaskUseCase(
                    TodoItemsRepositoryImpl(dispatcher = DispatchersImpl())
                )
            )
        )
    }
}