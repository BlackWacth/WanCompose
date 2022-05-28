package com.hzw.wan.ui.home

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.hzw.wan.R

@Composable
fun HomeScreen(navController: NavController) {
    val viewModel: HomeViewModel = hiltViewModel()
    SwipeRefresh(
        state = rememberSwipeRefreshState(isRefreshing = true),
        onRefresh = { viewModel.getHomeData(0) }) {

        Column {
            LargeTopAppBar(title = {
                Text(text = stringResource(id = R.string.home_page))
            })
        }
    }
}