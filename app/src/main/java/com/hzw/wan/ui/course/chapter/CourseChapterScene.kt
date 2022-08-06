package com.hzw.wan.ui.course.chapter

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import coil.compose.AsyncImage
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.hzw.wan.domain.model.Article
import com.hzw.wan.domain.model.Course
import com.hzw.wan.extend.logI
import com.hzw.wan.ui.main.enterArticleScreen

@Composable
fun CourseChapterScene(navController: NavController, course: Course) {
    val viewModel: CourseChapterViewModel = hiltViewModel()
    val lazyPagingItems = viewModel.listFlow.collectAsLazyPagingItems()
    Scaffold(
        topBar = {
            SmallTopAppBar(
                colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = MaterialTheme.colorScheme.primary),
                title = {
                    Text(
                        text = course.name,
                        style = MaterialTheme.typography.titleMedium,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "back",
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                }
            )
        }
    ) {
        when (lazyPagingItems.loadState.refresh) {
            LoadState.Loading -> {
                viewModel.refreshState.isRefreshing = true
            }
            else -> {
                viewModel.refreshState.isRefreshing = false
            }
        }
        SwipeRefresh(
            state = viewModel.refreshState,
            onRefresh = { lazyPagingItems.refresh() },
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            LazyColumn() {
                items(items = lazyPagingItems, key = { item -> item.id }) { item ->
                    item?.let { article ->
                        CourseChapterItem(
                            article = article,
                            pic = course.cover,
                            modifier = Modifier
                                .padding(top = 10.dp, start = 10.dp, end = 10.dp, bottom = 5.dp)
                                .height(90.dp)
                                .fillMaxWidth()
                        ) {
                            navController.enterArticleScreen(article)
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CourseChapterItem(
    article: Article,
    pic: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Surface(
        modifier = modifier,
        shadowElevation = 2.dp,
        shape = RoundedCornerShape(topStart = 24.dp, bottomEnd = 24.dp),
        onClick = onClick
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            AsyncImage(model = pic, contentDescription = "courseChapter")
            Column(modifier = Modifier.padding(horizontal = 8.dp)) {
                Text(
                    text = article.title,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    color = Color.Blue
                )
                Text(
                    text = article.author,
                    style = MaterialTheme.typography.labelMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(top = 2.dp)
                )
                Text(
                    text = "发布时间：${article.publishDate}",
                    style = MaterialTheme.typography.bodySmall.copy(color = Color.Gray),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        }
    }
}

