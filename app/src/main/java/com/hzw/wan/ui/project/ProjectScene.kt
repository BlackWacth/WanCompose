package com.hzw.wan.ui.project

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemsIndexed
import com.hzw.wan.domain.model.Article
import com.hzw.wan.domain.model.ProjectCategory
import com.hzw.wan.ui.home.ArticleItem
import com.hzw.wan.ui.main.enterArticleScreen
import com.hzw.wan.ui.view.WanTab
import com.hzw.wan.ui.view.WanTabRow

@Composable
fun ProjectScreen(navController: NavController) {
    val viewModel: ProjectViewModel = hiltViewModel()
    val tabState: ProjectTabState by viewModel.tabState.collectAsState()
    val contentState by viewModel.contentState.collectAsState()

    when (contentState) {
        ProjectContentState.Loading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(modifier = Modifier)
            }
        }
        is ProjectContentState.Content -> {
            val articleItems =
                (contentState as ProjectContentState.Content).pagingData.collectAsLazyPagingItems()
            ProjectContent(
                tabState,
                articleItems,
                tabClickAndSelected = { index, category ->
                    viewModel.switchTab(index)
                },
                articleItemClick = {
                    navController.enterArticleScreen(it)
                })
        }
        ProjectContentState.Error -> {

        }
    }
}

@Composable
fun ProjectContent(
    tabState: ProjectTabState,
    articleItems: LazyPagingItems<Article>,
    tabClickAndSelected: (Int, ProjectCategory) -> Unit,
    articleItemClick: (Article) -> Unit
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        WanTabRow(selectedTabIndex = tabState.currentIndex) {
            tabState.tabs.forEachIndexed { index, projectCategory ->
                WanTab(
                    selected = index == tabState.currentIndex,
                    onClick = { tabClickAndSelected(index, projectCategory) }) {
                    Text(text = projectCategory.name)
                }
            }
        }

        LazyColumn {
            itemsIndexed(articleItems) { index, item ->
                item?.let {
                    ArticleItem(
                        article = item, modifier = Modifier
                            .padding(
                                start = 16.dp,
                                end = 16.dp,
                                bottom = 16.dp,
                                top = if (index == 0) 16.dp else 0.dp
                            )
                            .height(96.dp)
                    ) {
                        articleItemClick(it)
                    }
                }
            }
        }
    }
}
