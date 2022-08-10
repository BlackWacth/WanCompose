package com.hzw.wan.ui.system

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.hzw.wan.R
import com.hzw.wan.domain.Resource
import com.hzw.wan.domain.model.AndroidSystem
import com.hzw.wan.domain.model.AndroidSystemChildren
import com.hzw.wan.extend.logD
import com.hzw.wan.ui.main.enterSearchScreen
import com.hzw.wan.ui.main.enterSystemArticleList
import com.hzw.wan.ui.theme.Purple80

@Composable
fun SystemScreen(navController: NavController) {
    val viewModel: SystemViewModel = hiltViewModel()
    val resource by viewModel.liveData.observeAsState()
    Scaffold(topBar = {
        CenterAlignedTopAppBar(
            colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                containerColor = MaterialTheme.colorScheme.primary
            ),
            title = {
                Text(
                    text = stringResource(id = R.string.home_system),
                    color = MaterialTheme.colorScheme.onPrimary,
                    style = MaterialTheme.typography.titleLarge
                )
            },
            actions = {
                IconButton(onClick = {
                    navController.enterSearchScreen()
                }) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search",
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }
        )
    }) {
        when (resource) {
            is Resource.Loading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(modifier = Modifier)
                }
            }

            is Resource.Success -> {
                SystemContent(
                    modifier = Modifier.padding(it),
                    list = resource?.data,
                    selected = viewModel.selectedSystemState.value,
                    leftClick = {
                        viewModel.selectedSystemState.value = it
                    },
                    rightClick = {
                        navController.enterSystemArticleList(it)
                    })
            }
        }
    }


}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SystemContent(
    modifier: Modifier = Modifier,
    list: List<AndroidSystem>?,
    selected: AndroidSystem,
    leftClick: (AndroidSystem) -> Unit,
    rightClick: (AndroidSystemChildren) -> Unit
) {
    if (list.isNullOrEmpty()) {
        return
    }
    "list = ${list.joinToString()}".logD()
    Row {
        LazyColumn(
            modifier = Modifier.weight(0.3f)
        ) {
            items(list, key = { it.id }) {
                Box(
                    modifier = Modifier
                        .background(color = if (it.id == selected.id) Color.White else MaterialTheme.colorScheme.primary)
                        .fillMaxWidth()
                        .height(50.dp)
                        .clickable {
                            leftClick(it)
                        },
                    contentAlignment = Alignment.CenterStart
                ) {
                    Text(
                        text = it.name,
                        modifier = Modifier.padding(horizontal = 10.dp),
                        maxLines = 1,
                        color = if (it.id == selected.id) Color.Black else MaterialTheme.colorScheme.onPrimary,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.weight(0.7f),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            contentPadding = PaddingValues(10.dp),
        ) {
            items(selected.children, key = { it.id }) { item ->
                Card(
                    shape = RoundedCornerShape(topStart = 24.dp, bottomEnd = 24.dp),
                    elevation = 4.dp,
                    onClick = { rightClick(item) }) {
                    Column {
                        Image(
                            painter = painterResource(id = R.drawable.img_default),
                            contentDescription = ""
                        )
                        Text(
                            text = item.name,
                            modifier = Modifier.padding(vertical = 10.dp, horizontal = 4.dp),
                            fontSize = 12.sp,
                            overflow = TextOverflow.Ellipsis,
                            maxLines = 1
                        )
                    }
                }
            }
        }
    }
}