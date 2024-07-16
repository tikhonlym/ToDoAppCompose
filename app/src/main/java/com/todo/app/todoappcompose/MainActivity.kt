package com.todo.app.todoappcompose

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
import com.todo.core.callback.NetworkStatusCallback
import com.todo.core.navigation.EditScreenDestination
import com.todo.core.navigation.HomeScreenDestination
import com.todo.core.theme.AppTheme
import com.todo.featureedit.EditScreen
import com.todo.featurehome.HomeScreen
import com.todo.featurehome.HomeViewModel
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

