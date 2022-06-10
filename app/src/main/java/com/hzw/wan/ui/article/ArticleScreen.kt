package com.hzw.wan.ui.article

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.webkit.WebResourceRequest
import android.webkit.WebView
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.google.accompanist.web.AccompanistWebViewClient
import com.google.accompanist.web.WebView
import com.google.accompanist.web.rememberWebViewNavigator
import com.google.accompanist.web.rememberWebViewState
import com.hzw.wan.domain.model.Article
import com.hzw.wan.extend.parseHtmlToText
import com.hzw.wan.ui.theme.Purple40

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("SetJavaScriptEnabled")
@Composable
fun ArticleDetailScreen(navController: NavController, article: Article) {

    val systemUiController = rememberSystemUiController()
    SideEffect {
        systemUiController.setNavigationBarColor(color = Color.Transparent)
    }

    val webState = rememberWebViewState(url = article.link)

    val webNavigator = rememberWebViewNavigator()
    val webViewClient = remember {
        object : AccompanistWebViewClient() {
            override fun shouldOverrideUrlLoading(
                view: WebView?,
                request: WebResourceRequest?
            ): Boolean {
                val url = request?.url?.toString() ?: return false
                try {
                    if (!url.startsWith("http") || !url.startsWith("https")) {
                        view?.context?.startActivity(
                            Intent(
                                Intent.ACTION_VIEW,
                                Uri.parse(article.link)
                            )
                        )
                        return true
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    return false
                }
                return super.shouldOverrideUrlLoading(view, request)
            }
        }
    }
    val context = LocalContext.current
    Scaffold(
        topBar = {
            SmallTopAppBar(
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary
                ),
                title = {
                    Text(
                        text = article.title.parseHtmlToText(),
                        fontSize = 16.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        color = MaterialTheme.colorScheme.onPrimary,
                        style = MaterialTheme.typography.titleLarge
                    )
                },
                navigationIcon = {
                    IconButton(onClick = {
                        if (webNavigator.canGoBack) {
                            webNavigator.navigateBack()
                        } else {
                            navController.navigateUp()
                        }
                    }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "back",
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                },
                actions = {
                    IconButton(onClick = {
                        share(context, article.title, article.link)
                    }) {
                        Icon(
                            imageVector = Icons.Default.Share,
                            contentDescription = "share",
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        WebView(
            state = webState,
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize(),
            onCreated = {
                it.settings.apply {
                    domStorageEnabled = true
                    javaScriptEnabled = true
                }
            },
            navigator = webNavigator,
            client = webViewClient
        )
    }
}

fun share(context: Context, title: String, post: String) {
    val intent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_TITLE, title)
        putExtra(Intent.EXTRA_TEXT, post)
    }
    context.startActivity(Intent.createChooser(intent, "Share $title"))
}