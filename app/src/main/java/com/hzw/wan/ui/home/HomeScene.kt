package com.hzw.wan.ui.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.hzw.wan.R
import com.hzw.wan.domain.model.Article
import com.hzw.wan.domain.model.Banner
import com.hzw.wan.extend.logW
import com.hzw.wan.ui.main.enterArticleScreen
import com.zj.banner.BannerPager
import com.zj.banner.utils.ImageLoader

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController, viewModel: HomeViewModel) {
    val refreshState = rememberSwipeRefreshState(isRefreshing = viewModel.refreshState)
    val articlePagingItems = viewModel.articleListFlow.collectAsLazyPagingItems()
    "size = ${articlePagingItems.itemCount}".logW()
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary
                ),
                title = {
                    Text(
                        text = stringResource(id = R.string.home_page),
                        color = MaterialTheme.colorScheme.onPrimary,
                        style = MaterialTheme.typography.titleLarge
                    )
                },
                actions = {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Search",
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                }
            )
        }) {
        SwipeRefresh(
            state = refreshState,
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            onRefresh = {
                viewModel.getBannerList()
                articlePagingItems.refresh()
            }) {
            LazyColumn() {
                item {
                    HomeBanner(
                        bannerList = viewModel.bannerState,
                        modifier = Modifier.padding(bottom = 10.dp)
                    ) {
                        val article = Article(title = it.title, link = it.url)
                        navController.enterArticleScreen(article)
                    }
                }
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

@Composable
fun HomeBanner(
    bannerList: List<Banner>,
    modifier: Modifier = Modifier,
    onBannerClick: (Banner) -> Unit
) {
    if (bannerList.isEmpty()) {
        return
    }
    BannerPager(
        items = bannerList,
        modifier = modifier,
        indicatorGravity = Alignment.BottomCenter,
        onBannerClick = onBannerClick
    )
}

@Composable
fun ArticleItem(
    article: Article,
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(topStart = 24.dp, bottomEnd = 24.dp),
    onClick: () -> Unit
) {
    Surface(
        tonalElevation = 1.dp,
        modifier = modifier,
        shape = shape
    ) {
        Row(modifier = Modifier.clickable(onClick = onClick)) {
            ImageLoader(
                data = article.pic.ifBlank { R.drawable.img_default },
                modifier = Modifier.aspectRatio(1f)
            )
            Column(modifier = Modifier.padding(horizontal = 10.dp, vertical = 8.dp)) {
                Text(
                    text = article.title,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                        .weight(1f)
                )
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = article.superChapterName,
                        style = MaterialTheme.typography.labelMedium,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier
                            .weight(1f)
                            .wrapContentWidth(align = Alignment.Start)
                    )
                    Text(
                        text = article.author.ifEmpty { article.shareUser },
                        style = MaterialTheme.typography.labelMedium,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier
                            .weight(1f)
                            .wrapContentWidth(align = Alignment.Start)
                    )
                }
            }
        }
    }
}





