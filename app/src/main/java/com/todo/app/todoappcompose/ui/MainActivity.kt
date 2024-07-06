package com.todo.app.todoappcompose.ui

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.todo.app.todoappcompose.app.theme.AppTheme
import com.todo.app.todoappcompose.ui.edit.EditScreen
import com.todo.app.todoappcompose.ui.edit.EditScreenDestination
import com.todo.app.todoappcompose.ui.home.HomeScreen
import com.todo.app.todoappcompose.ui.home.HomeScreenDestination
import com.todo.app.todoappcompose.ui.home.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                MainScreenApp(this)
            }
        }
    }
}

@Composable
fun MainScreenApp(
    context: Context,
) {
    val navController = rememberNavController()
    val networkStatusCallback = remember { NetworkStatusCallback(context) }
    val homeViewModel: HomeViewModel = hiltViewModel()
    if (networkStatusCallback.isNetworkAvailable.value) {
        homeViewModel.refreshList()
    }
    NavHost(
        navController = navController,
        startDestination = HomeScreenDestination,
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None },
    ) {
        composable<HomeScreenDestination> {
            HomeScreen(
                viewModel = homeViewModel,
                networkStatusCallback = networkStatusCallback,
                onNavigateToEditScreen = {
                    navController.navigate(EditScreenDestination(it))
                }
            )
        }
        composable<EditScreenDestination> { backStackEntry ->
            val editScreen: EditScreenDestination = backStackEntry.toRoute()
            EditScreen(
                id = editScreen.id,
                onNavigateUp = {
                    navController.navigateUp()
                }
            )
        }
    }
}

