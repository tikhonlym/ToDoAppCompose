package com.todo.featurehome

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.todo.core.R
import com.todo.core.callback.NetworkStatusCallback
import com.todo.core.theme.AppTheme
import com.todo.core.theme.component.CustomSnackBar
import com.todo.core.theme.component.SnackBarData
import com.todo.core.theme.shimmerBackground
import com.todo.featurehome.items.CreateTaskUiListItem
import com.todo.featurehome.items.InternetStatusUiItem
import com.todo.featurehome.items.NewTaskFloatingButton
import com.todo.featurehome.items.SwitchVisibilityButton
import com.todo.featurehome.items.SyncButton
import com.todo.featurehome.items.TodoUiItem
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    onNavigateToEditScreen: (String?) -> Unit,
    networkStatusCallback: NetworkStatusCallback,
) {

    LaunchedEffect(key1 = Unit) {
        networkStatusCallback.registerNetworkCallback()
        viewModel.checkSyncNeeded()
        viewModel.getLocalList()
    }

    val isRefreshing = viewModel.isRefreshing.collectAsState()
    val pullRefreshState = rememberPullRefreshState(
        refreshing = isRefreshing.value,
        onRefresh = { viewModel.refreshList() }
    )

    val uiState = viewModel.uiState.collectAsState()
    when (uiState.value) {
        HomeScreenState.Loading -> {
            LoadingHomeScreen()
        }

        HomeScreenState.Complete -> {
            val snackBarState =
                viewModel.showSnackBar.collectAsState()

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(AppTheme.colorScheme.backPrimary)
                    .pullRefresh(pullRefreshState)
            ) {

                Box(
                    Modifier
                        .align(Alignment.BottomCenter)
                        .zIndex(2f)
                        .padding(bottom = 30.dp)
                        .fillMaxWidth()
                ) {
                    if (viewModel.checkSyncNeeded()) {
                        snackBarState.value?.let {
                            ShowCustomSnackBar(
                                data = it,
                                duration = 2000L
                            )
                        }
                    }
                }

                HomeScreenComponent(onNavigateToEditScreen, viewModel, networkStatusCallback)

                Column(
                    Modifier
                        .align(Alignment.BottomEnd)
                        .padding(end = 16.dp, bottom = 24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    val syncNeeded = viewModel.syncNeeded.collectAsState()
                    if (syncNeeded.value) {
                        Text(
                            stringResource(id = R.string.txt_sync_needed),
                            color = AppTheme.colorScheme.colorOrange,
                            style = AppTheme.typographyScheme.subhead,
                            textAlign = TextAlign.Center
                        )
                        Spacer(Modifier.height(4.dp))
                        SyncButton {
                            viewModel.refreshList()
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    NewTaskFloatingButton(
                        onNewTask = {
                            onNavigateToEditScreen(null)
                        },
                        modifier = Modifier
                    )
                }

                PullRefreshIndicator(
                    refreshing = isRefreshing.value,
                    state = pullRefreshState,
                    modifier = Modifier.align(Alignment.TopCenter)
                )
            }
        }
    }

    BackHandler {
        networkStatusCallback.unregisterNetworkCallback()
    }
}

@Composable
fun ShowCustomSnackBar(
    modifier: Modifier = Modifier,
    data: SnackBarData,
    duration: Long,
) {
    var visibleState by remember { mutableStateOf(true) }
    LaunchedEffect(key1 = Unit) {
        delay(duration)
        visibleState = false
    }
    AnimatedVisibility(visible = visibleState) {
        CustomSnackBar(
            modifier = modifier,
            data = data
        )
    }
}

@Composable
private fun HomeScreenComponent(
    onNavigateToEditScreen: (String?) -> Unit,
    viewModel: HomeViewModel,
    networkStatusCallback: NetworkStatusCallback,
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

    val isNetworkAvailable by remember { networkStatusCallback.isNetworkAvailable }

    val todoList = viewModel.taskList.collectAsState()
    val showCompleted = viewModel.showCompletedTasks.collectAsState()
    val countCompletedTasks = viewModel.countCompleted.collectAsState()
    var onTaskClickEnabled by remember { mutableStateOf(true) }

    CollapsedTopBar(
        modifier = Modifier.zIndex(2f),
        isCollapsed = isCollapsed,
        internetWorking = isNetworkAvailable
    )

    LazyColumn(state = listState) {

        item {
            ExpandedToolbar(
                onSwitch = { show ->
                    viewModel.showCompletedTasks(show)
                },
                countCompleted = countCompletedTasks.value,
                show = showCompleted.value,
                internetWorking = isNetworkAvailable
            )
        }

        items(count = todoList.value.size) { index ->
            val curItem = todoList.value[index]
            TodoUiItem(
                onCompleteClick = { task, isDone ->
                    viewModel.completeTask(task, isDone)
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
fun LoadingHomeScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AppTheme.colorScheme.backPrimary)
    ) {
        Spacer(Modifier.height(50.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 60.dp, end = 60.dp)
                .defaultMinSize(minHeight = 38.dp)
                .shimmerBackground(RoundedCornerShape(8.dp)),
        )
        Spacer(modifier = Modifier.height(2.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 60.dp, end = 60.dp)
                .defaultMinSize(minHeight = 20.dp)
                .shimmerBackground(RoundedCornerShape(8.dp)),
        )
        Spacer(modifier = Modifier.height(16.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(start = 8.dp, end = 8.dp)
                .defaultMinSize(minHeight = 20.dp)
                .shimmerBackground(RoundedCornerShape(8.dp)),
        )
    }
}

@Preview
@Composable
private fun LoadingHomeScreenPrev() {
    AppTheme {
        LoadingHomeScreen()
    }
}

private val EXPANDED_TOP_BAR_HEIGHT = 85.dp
private val COLLAPSED_TOP_BAR_HEIGHT = 56.dp

@Composable
private fun ExpandedToolbar(
    onSwitch: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    countCompleted: Int,
    show: Boolean,
    internetWorking: Boolean,
) {
    var localShowState by remember { mutableStateOf(show) }
    Column(
        modifier = modifier
            .padding(start = 60.dp, top = 50.dp)
            .fillMaxWidth()
            .height(EXPANDED_TOP_BAR_HEIGHT)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = stringResource(id = R.string.my_tasks_title),
                style = AppTheme.typographyScheme.largeTitle,
                color = AppTheme.colorScheme.labelPrimary
            )
            Spacer(modifier = Modifier.width(16.dp))
            InternetStatusUiItem(internetWorking = internetWorking)
        }
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
    internetWorking: Boolean,
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
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        modifier = Modifier.padding(16.dp),
                        text = stringResource(id = R.string.my_tasks_title),
                        color = AppTheme.colorScheme.labelPrimary,
                        style = AppTheme.typographyScheme.title,
                    )
                    InternetStatusUiItem(internetWorking = internetWorking)
                }
                HorizontalDivider(
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
        CollapsedTopBar(isCollapsed = true, internetWorking = false)
    }
}
