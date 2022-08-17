package com.hzw.wan.ui.mine

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Divider
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.hzw.wan.R
import com.hzw.wan.ui.account.LoginScreen
import com.hzw.wan.ui.main.enterLogin

@Composable
fun MineScreen(navController: NavController) {
    Column(modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .background(color = MaterialTheme.colorScheme.primary)
                .fillMaxWidth()
                .height(180.dp)
                .clickable {
                    navController.enterLogin()
                },
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Image(
                    painter = painterResource(id = R.drawable.img_default),
                    contentDescription = "header",
                    modifier = Modifier
                        .clip(CircleShape)
                        .size(70.dp, 70.dp),
                    contentScale = ContentScale.Crop
                )
                Text(
                    text = "未登录",
                    style = MaterialTheme.typography.titleLarge.copy(color = MaterialTheme.colorScheme.onPrimary),
                    modifier = Modifier.padding(top = 10.dp)
                )
            }
        }

        SettingItem(icon = painterResource(id = R.drawable.ic_palette), title = "主题") {
        }
    }

}

@Composable
fun SettingItem(icon: Painter, title: String, modifier: Modifier = Modifier, onClick: () -> Unit) {
    Column(modifier = modifier.clickable(onClick = onClick)) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .padding(start = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = icon,
                contentDescription = "theme"
            )
            Text(
                text = title,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier
                    .padding(start = 10.dp)
                    .weight(1f)
            )
            Icon(
                imageVector = Icons.Default.KeyboardArrowRight,
                contentDescription = "arrowRight",
                modifier = Modifier.wrapContentWidth(Alignment.End)
            )
        }
        Divider(modifier = Modifier.padding(horizontal = 10.dp))
    }
}