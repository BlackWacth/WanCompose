package com.hzw.wan.ui.main

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.hzw.wan.R
import com.hzw.wan.ui.home.HomeScreen
import com.hzw.wan.ui.home.HomeViewModel
import com.hzw.wan.ui.mine.MineScreen
import com.hzw.wan.ui.course.CourseScreen
import com.hzw.wan.ui.project.ProjectScreen
import com.hzw.wan.ui.system.SystemScreen
import com.hzw.wan.ui.theme.Purple40

sealed class Screen(
    val route: String,
    @StringRes val title: Int,
    @DrawableRes val normalIcon: Int,
    @DrawableRes val selectedIcon: Int
) {
    object Home : Screen(
        "Home",
        R.string.home_page,
        R.drawable.ic_home,
        R.drawable.ic_home_fill
    )

    object System : Screen(
        "System",
        R.string.home_system,
        R.drawable.ic_system,
        R.drawable.ic_system_fill
    )

    object Project : Screen(
        "Project",
        R.string.project,
        R.drawable.ic_project,
        R.drawable.ic_project_fill
    )

    object Course : Screen(
        "OfficialAccount",
        R.string.course,
        R.drawable.ic_official,
        R.drawable.ic_official_fill
    )

    object Mine : Screen(
        "Mine",
        R.string.mine,
        R.drawable.ic_mine,
        R.drawable.ic_mine_fill
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(navHostController: NavHostController) {
    val viewModel: HomeViewModel = hiltViewModel()
    val items = listOf(
        Screen.Home,
        Screen.System,
        Screen.Project,
        Screen.Course,
        Screen.Mine,
    )

    val systemUiController = rememberSystemUiController()
    SideEffect {
        systemUiController.setNavigationBarColor(color = Purple40)
    }

    val navController = rememberNavController()
    Scaffold(
        bottomBar = {
            NavigationBar(containerColor = MaterialTheme.colorScheme.primary) {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination
                items.forEachIndexed { index, screen ->
                    NavigationBarItem(
                        selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                        onClick = {
                            navController.navigate(screen.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        icon = {
                            Icon(
                                painter = painterResource(id = screen.selectedIcon),
                                contentDescription = screen.route,
                                tint = if (currentDestination?.hierarchy?.any { it.route == screen.route } == true) {
                                    Purple40
                                } else {
                                    Color.Gray
                                }
                            )
                        },
                        label = {
                            Text(
                                text = stringResource(id = screen.title),
                                color = if (currentDestination?.hierarchy?.any { it.route == screen.route } == true) {
                                    Color.White
                                } else {
                                    Color.Gray
                                })
                        }
                    )
                }
            }
        }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(paddingValues)
        ) {
            composable(route = Screen.Home.route) { HomeScreen(navController = navHostController, viewModel) }
            composable(route = Screen.System.route) { SystemScreen(navController = navHostController) }
            composable(route = Screen.Project.route) { ProjectScreen(navController = navHostController) }
            composable(route = Screen.Course.route) { CourseScreen(navController = navHostController) }
            composable(route = Screen.Mine.route) { MineScreen(navController = navHostController) }
        }
    }
}