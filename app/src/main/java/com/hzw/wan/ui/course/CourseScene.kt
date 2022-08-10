package com.hzw.wan.ui.course

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.hzw.wan.R
import com.hzw.wan.domain.Result
import com.hzw.wan.domain.model.Course
import com.hzw.wan.extend.logI
import com.hzw.wan.ui.main.enterCourseChapter
import kotlin.random.Random
import kotlin.time.ExperimentalTime
import kotlin.time.measureTimedValue

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CourseScreen(navController: NavController) {
    val viewModel: CourseViewModel = hiltViewModel()
    val state by viewModel.courseStateFlow.collectAsState()
    Scaffold(topBar = {
        CenterAlignedTopAppBar(
            title = {
                Text(
                    text = stringResource(id = R.string.course),
                    style = MaterialTheme.typography.titleMedium.copy(color = MaterialTheme.colorScheme.onPrimary)
                )
            },
            colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = MaterialTheme.colorScheme.primary)
        )
    }) {
        SwipeRefresh(
            state = viewModel.refreshState,
            onRefresh = {
                viewModel.getCourseList()
            },
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            when (state) {
                is Result.Loading -> {}
                is Result.Success<List<Course>> -> {
                    val list = (state as Result.Success<List<Course>>).data
                    LazyColumn() {
                        items(
                            items = list,
                            key = { course ->
                                course.id
                            }
                        ) { item ->
                            CourseItem(
                                course = item,
                                modifier = Modifier
                                    .padding(top = 10.dp, start = 10.dp, end = 10.dp)
                                    .height(90.dp)
                                    .fillMaxWidth()
                            ) {
                                navController.enterCourseChapter(item)
                            }
                        }
                    }
                }
                is Result.Error -> {}
            }
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CourseItem(course: Course, modifier: Modifier = Modifier, onClick: () -> Unit) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(topStart = 24.dp, bottomEnd = 24.dp),
        onClick = onClick,
        shadowElevation = 2.dp
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            AsyncImageWithError(model = course.cover, contentDescription = course.name)
            Column(modifier = Modifier.padding(start = 8.dp, end = 8.dp)) {
                Text(
                    text = course.name,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = "作者：${course.author}",
                    style = MaterialTheme.typography.labelMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(top = 2.dp)
                )
                Text(
                    text = course.desc,
                    style = MaterialTheme.typography.bodySmall.copy(color = Color.Gray),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        }
    }
}

private val defaultImageList = listOf(
    R.drawable.ic_graph_line,
    R.drawable.ic_launch_alt,
    R.drawable.ic_layout,
    R.drawable.ic_lightbulb,
    R.drawable.ic_kotlin_hero,
)

fun getRandomDefaultImage(): Int {
    val randomIndex = defaultImageList.indices.random()
    return defaultImageList[randomIndex]
}

@Composable
fun AsyncImageWithError(
    model: Any?,
    contentDescription: String?,
    modifier: Modifier = Modifier,
) {
    val painter by rememberUpdatedState(newValue = painterResource(id = getRandomDefaultImage()))
    AsyncImage(
        model = model,
        contentDescription = contentDescription,
        error = painter,
        modifier = modifier.aspectRatio(1f),
        fallback = painter,
    )
}


@Preview()
@Composable
fun TestCourseItem() {
    CourseItem(
        course = Course(
            id = 0,
            name = "C 语言入门教程_阮一峰",
            author = "阮一峰",
            cover = "https://www.wanandroid.com/blogimgs/f1cb8d34-82c1-46f7-80fe-b899f56b69c1.png",
            desc = "本教程完整介绍 HTML 语言的所有内容，既可以当作初学者的入门教程，也可以用作参考手册查阅语法"
        ),
        modifier = Modifier
            .padding(top = 10.dp, start = 10.dp, end = 10.dp)
            .height(90.dp)
    ) {

    }
}