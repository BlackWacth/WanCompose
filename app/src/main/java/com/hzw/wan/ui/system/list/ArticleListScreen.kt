package com.hzw.wan.ui.system.list

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.hzw.wan.domain.model.AndroidSystemChildren
import com.hzw.wan.extend.logI
import com.hzw.wan.ui.home.ArticleItem
import com.hzw.wan.ui.main.enterArticleScreen

@Composable
fun ArticleListScreen(navController: NavController, systemChild: AndroidSystemChildren) {
    val viewModel: ArticleListViewModel = hiltViewModel()
    val systemUiController = rememberSystemUiController()
    SideEffect {
        systemUiController.setNavigationBarColor(color = Color.Transparent)
    }

//    LaunchedEffect(systemChild) {
//        "LaunchedEffect ==> $systemChild".logI()
//        viewModel.getList(systemChild.id)
//    }
    val refreshState = rememberSwipeRefreshState(isRefreshing = viewModel.refreshState)
    val articlePagingItems = viewModel.listFlow.collectAsLazyPagingItems()
    Scaffold(
        topBar = {
            SmallTopAppBar(
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary
                ),
                title = {
                    Text(
                        text = systemChild.name,
                        fontSize = 16.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        color = MaterialTheme.colorScheme.onPrimary,
                        style = MaterialTheme.typography.titleLarge
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "back",
                            tint = Color.White
                        )
                    }
                }
            )
        }
    ) {
        SwipeRefresh(
            state = refreshState,
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            onRefresh = {
                articlePagingItems.refresh()
            }
        ) {
            LazyColumn(modifier = Modifier.padding(top = 16.dp)) {
                items(articlePagingItems) { item ->
                    item?.let {
                        ArticleItem(
                            article = item,
                            modifier = Modifier
                                .height(96.dp)
                                .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
                        ) {
                            navController.enterArticleScreen(item)
                        }
                    }
                }
            }
        }
    }
}