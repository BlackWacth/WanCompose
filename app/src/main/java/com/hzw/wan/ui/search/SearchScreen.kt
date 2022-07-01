package com.hzw.wan.ui.search

import android.graphics.Point
import android.graphics.PointF
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.*
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*
import androidx.navigation.NavController
import com.hzw.wan.extend.logI
import com.hzw.wan.setNavigationBarAndSystemBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(navController: NavController) {
    setNavigationBarAndSystemBar(navigationColor = MaterialTheme.colorScheme.surface)
    var search by remember {
        mutableStateOf("")
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = MaterialTheme.colorScheme.primary),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = {
                        navController.navigateUp()
                    }, modifier = Modifier
                        .padding(start = 5.dp)
                        .offset(y = 4.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "back",
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                }
                OutlinedTextField(
                    value = search,
                    onValueChange = {
                        search = it
                    },
                    label = {
                        Text(text = "请输入搜索关键字")
                    },
                    maxLines = 1,
                    singleLine = true,
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        textColor = MaterialTheme.colorScheme.onPrimary,
                        unfocusedLabelColor = MaterialTheme.colorScheme.onPrimary,
                        unfocusedBorderColor = MaterialTheme.colorScheme.onPrimary,
                        focusedBorderColor = MaterialTheme.colorScheme.onPrimary,
                        focusedLabelColor = MaterialTheme.colorScheme.onPrimary,
                        cursorColor = MaterialTheme.colorScheme.onPrimary
                    ),
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Search,
                        keyboardType = KeyboardType.Text
                    ),
                    keyboardActions = KeyboardActions {
                    },
                    textStyle = TextStyle(fontSize = 16.sp),
                    modifier = Modifier
                        .weight(1f)
                        .padding(vertical = 8.dp),
                    trailingIcon = {
                        IconButton(onClick = { search = "" }) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "clear",
                                tint = MaterialTheme.colorScheme.onPrimary
                            )
                        }
                    }
                )

                Button(
                    onClick = { /*TODO*/ },
                    modifier = Modifier
                        .wrapContentWidth(Alignment.End)
                        .offset(y = 4.dp),
                    contentPadding = PaddingValues(10.dp)
                ) {
                    Text(
                        text = "搜索",
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onPrimary,
                        maxLines = 1,
                    )
                }
            }
        }
    ) {
        val chipLabelList = listOf(
            "Java",
            "Kotlin",
            "C",
            "C++",
            "Python",
            "Ruby",
            "C#",
            "Dart",
            "Flutter",
            "Go",
            "Lua",
            "JavaScript",
            "Visual Basic",
            "Sql",
            "PHP",
            "R",
            "Swift",
            "Perl"
        )
//        AssistChip(onClick = { /*TODO*/ }, label = {
//            Text(text = "Swift", fontSize = 14.sp)
//        }, modifier = Modifier.padding(it))


        ChipLayout(modifier = Modifier.padding(it)) {
            chipLabelList.forEach {
                ElevatedAssistChip(onClick = { /*TODO*/ }, label = {
                    Text(text = it, fontSize = 14.sp)
                })
            }
        }
    }
}

//@OptIn(ExperimentalMaterial3Api::class)
//@Preview
//@Composable
//fun PreAssistChip() {
//    ElevatedAssistChip(onClick = { /*TODO*/ }, label = {
//        Text(text = "Kotlin", fontSize = 14.sp)
//    }, elevation = null)
//}

@Composable
fun ChipLayout(
    modifier: Modifier = Modifier,
    vSpacing: Dp = 0.dp,
    hSpacing: Dp = 10.dp,
    content: @Composable () -> Unit
) {
    Layout(content = content, modifier = modifier) { measurables, constraints ->
        val placeableList = measurables.map {
            it.measure(constraints)
        }
        "placeableList => ${placeableList.joinToString(transform = {
            "w = ${it.width}, h = ${it.height}"
        }, separator = "\n")}".logI()
        val vSpacingPx = vSpacing.roundToPx()
        val hSpacingPx = hSpacing.roundToPx()

        //控件宽度
        val width = constraints.maxWidth
        "constraints.maxWidth = $width, vSpacingPx = ${vSpacingPx}, hSpacingPx = ${hSpacingPx}".logI()
        //当前放置的最高高度
        var maxHeight = 0
        var x = hSpacingPx
        var y = vSpacingPx
        val pointList = mutableListOf<Point>()
        placeableList.forEach { placeable ->
            //取最大
            maxHeight = maxHeight.coerceAtLeast(placeable.height)
            val nextX = x + placeable.width + hSpacingPx
            // 下一个控件的x坐标已经大于容器宽度，换行
            if (nextX > width) {
                //换行后的坐标
                x = hSpacingPx
                y += maxHeight + vSpacingPx
                pointList.add(Point(x, y))
                x += placeable.width + hSpacingPx
            } else { //不换行
                pointList.add(Point(x, y))
                x = nextX
            }
        }
        layout(width, y + maxHeight + vSpacingPx) {
            placeableList.forEachIndexed { index, placeable ->
                val point = pointList[index]
                placeable.placeRelative(x = point.x, y = point.y)
            }
        }
    }
}