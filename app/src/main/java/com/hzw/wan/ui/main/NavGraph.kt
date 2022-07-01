package com.hzw.wan.ui.main

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.gson.Gson
import com.hzw.wan.domain.model.Article
import com.hzw.wan.ui.article.ArticleDetailScreen
import com.hzw.wan.ui.search.SearchScreen
import java.net.URLEncoder

@Composable
fun NavGraph(startDestination: String = AppRouter.Main.route) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = startDestination) {
        //主页
        composable(route = AppRouter.Main.route) { MainScreen(navController) }

        //搜索
        composable(
            route = AppRouter.Search.route
        ) {
            SearchScreen(navController = navController)
        }

        //文章详情
        composable(
            route = AppRouter.ArticleDetail.finalRoute,
            arguments = AppRouter.ArticleDetail.arguments
        ) { navBackStackEntry ->
            navBackStackEntry.arguments?.getString(AppRouter.ArticleDetail.argument_url)?.let {
                val article = Gson().fromJson(it, Article::class.java)
                ArticleDetailScreen(navController = navController, article = article)
            }
        }
    }
}

sealed class AppRouter(val route: String) {

    object Main : AppRouter("Main")

    object Search : AppRouter("Search")

    object ArticleDetail : AppRouter("articleDetail") {

        const val argument_url = "art_article_detail"

        val finalRoute: String
            get() = "${route}/{${argument_url}}"

        val arguments = listOf(navArgument(argument_url) {
            type = NavType.StringType
        })
    }
}

fun NavController.enterArticleScreen(article: Article) {
    val json = Gson().toJson(article)
    val result = URLEncoder.encode(json, "utf-8")
    navigate("${AppRouter.ArticleDetail.route}/${result}")
}

fun NavController.enterSearchScreen() {
    navigate(AppRouter.Search.route)
}
