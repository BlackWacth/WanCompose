package com.hzw.wan.ui.main

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.gson.Gson
import com.hzw.wan.R
import com.hzw.wan.domain.model.Article
import com.hzw.wan.ui.article.ArticleScreen
import com.hzw.wan.ui.home.HomeScreen
import com.hzw.wan.ui.mine.MineScreen
import com.hzw.wan.ui.officialAccount.OfficialAccountScreen
import com.hzw.wan.ui.project.ProjectScreen
import com.hzw.wan.ui.system.SystemScreen
import java.net.URLEncoder

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

    object OfficialAccount : Screen(
        "OfficialAccount",
        R.string.official_account,
        R.drawable.ic_official,
        R.drawable.ic_official_fill
    )

    object Mine : Screen(
        "Mine",
        R.string.mine,
        R.drawable.ic_mine,
        R.drawable.ic_mine_fill
    )

    object ArticleDetail : Screen(route = "articleDetail", -1, -1, -1) {
        const val argArticleDetail: String = "article_detail_url"

        val finalRoute: String
            get() = "${route}/{${argArticleDetail}}"

    }
}

fun NavController.enterArticleScreen(article: Article) {
    val json = Gson().toJson(article)
    val result = URLEncoder.encode(json, "utf-8")
    navigate("${Screen.ArticleDetail.route}/${result}")
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun mainScreen() {
    val items = listOf(
        Screen.Home,
        Screen.System,
        Screen.Project,
        Screen.OfficialAccount,
        Screen.Mine,
    )
    val navController = rememberNavController()
    Scaffold(
        bottomBar = {
            NavigationBar {
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
                            if (currentDestination?.hierarchy?.any { it.route == screen.route } == true) {
                                Icon(
                                    painter = painterResource(id = screen.selectedIcon),
                                    contentDescription = screen.route
                                )
                            } else {
                                Icon(
                                    painter = painterResource(id = screen.normalIcon),
                                    contentDescription = screen.route
                                )
                            }
                        },
                        label = {
                            Text(text = stringResource(id = screen.title))
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
            composable(route = Screen.Home.route) { HomeScreen(navController = navController) }
            composable(route = Screen.System.route) { SystemScreen(navController = navController) }
            composable(route = Screen.Project.route) { ProjectScreen(navController = navController) }
            composable(route = Screen.OfficialAccount.route) { OfficialAccountScreen(navController = navController) }
            composable(route = Screen.Mine.route) { MineScreen(navController = navController) }
            composable(
                route = Screen.ArticleDetail.finalRoute,
                arguments = listOf(navArgument(name = Screen.ArticleDetail.argArticleDetail) {
                    type = NavType.StringType
                })
            ) { navBackStackEntry ->
                navBackStackEntry.arguments?.getString(Screen.ArticleDetail.argArticleDetail)?.let {
                    val article = Gson().fromJson(it, Article::class.java)
                    ArticleScreen(navController = navController, article = article)
                }
            }
        }
    }
}