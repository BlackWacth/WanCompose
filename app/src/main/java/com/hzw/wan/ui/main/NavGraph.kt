package com.hzw.wan.ui.main

import android.os.Bundle
import androidx.compose.runtime.Composable
import androidx.core.net.toUri
import androidx.navigation.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.gson.Gson
import com.hzw.wan.domain.model.AndroidSystemChildren
import com.hzw.wan.domain.model.Article
import com.hzw.wan.domain.model.Course
import com.hzw.wan.ui.account.LoginScreen
import com.hzw.wan.ui.account.RegisterScreen
import com.hzw.wan.ui.article.ArticleDetailScreen
import com.hzw.wan.ui.course.chapter.CourseChapterScene
import com.hzw.wan.ui.search.SearchScreen
import com.hzw.wan.ui.system.list.ArticleListScreen
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
        //体系分支列表
        composable(
            route = AppRouter.SystemArticleList.route,
        ) {
            it.arguments?.getParcelable<AndroidSystemChildren>(AppRouter.SystemArticleList.argumentSystemChild)
                ?.let { child ->
                    ArticleListScreen(navController = navController, systemChild = child)
                }
        }

        //教程章节
        composable(route = AppRouter.CourseChapter.route) {
            it.arguments?.getParcelable<Course>(AppRouter.CourseChapter.argCourse)?.let { course ->
                CourseChapterScene(navController = navController, course = course)
            }
        }

        // 登录
        composable(route = AppRouter.Login.route) {
            LoginScreen(navController = navController)
        }

        //注册
        composable(route = AppRouter.Register.route) {
            RegisterScreen(navController = navController)
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

    object SystemArticleList : AppRouter("SystemArticleList") {
        const val argumentSystemChild = "argumentSystemChild"
        val finalRoute: String = "$route/$argumentSystemChild"
        val arguments = listOf(navArgument(argumentSystemChild) {
            type = NavType.ParcelableType(AndroidSystemChildren::class.java)
        })
    }

    object CourseChapter : AppRouter("CourseChapter") {
        const val argCourse = "argCourse"
    }

    object Login : AppRouter("Login")

    object Register : AppRouter("Register")
}

fun NavController.enterArticleScreen(article: Article) {
    val json = Gson().toJson(article)
    val result = URLEncoder.encode(json, "utf-8")
    navigate("${AppRouter.ArticleDetail.route}/${result}")
}

fun NavController.enterSearchScreen() {
    navigate(AppRouter.Search.route)
}

fun NavController.enterLogin() {
    navigate(AppRouter.Login.route)
}

fun NavController.enterRegister() {
    navigate(AppRouter.Register.route) {
        popUpTo(AppRouter.Main.route)
    }
}

/**
 * 进入体系专题列表
 * @param child
 */
fun NavController.enterSystemArticleList(child: AndroidSystemChildren) {
    navigate(
        AppRouter.SystemArticleList.route,
        args = Bundle().apply {
            putParcelable(
                AppRouter.SystemArticleList.argumentSystemChild,
                child
            )
        })
}

/**
 * 进入教程章节
 * @param course
 */
fun NavController.enterCourseChapter(course: Course) {
    navigate(
        AppRouter.CourseChapter.route,
        args = Bundle().apply {
            putParcelable(AppRouter.CourseChapter.argCourse, course)
        }
    )
}

/**
 * 可以传递Parcelable参数
 * @param route
 * @param args
 * @param navOptions
 * @param navigatorExtras
 */
fun NavController.navigate(
    route: String,
    args: Bundle,
    navOptions: NavOptions? = null,
    navigatorExtras: Navigator.Extras? = null
) {
    val routeLink = NavDeepLinkRequest
        .Builder
        .fromUri(NavDestination.createRoute(route).toUri())
        .build()

    val deepLinkMatch = graph.matchDeepLink(routeLink)
    if (deepLinkMatch != null) {
        val destination = deepLinkMatch.destination
        val id = destination.id
        navigate(id, args, navOptions, navigatorExtras)
    } else {
        navigate(route, navOptions, navigatorExtras)
    }
}