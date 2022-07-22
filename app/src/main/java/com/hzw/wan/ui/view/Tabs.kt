package com.hzw.wan.ui.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun WanTabRow(
    selectedTabIndex: Int,
    modifier: Modifier = Modifier,
    tabs: @Composable () -> Unit
) {
    ScrollableTabRow(
        selectedTabIndex = selectedTabIndex,
        modifier = modifier,
        tabs = tabs,
        contentColor = MaterialTheme.colorScheme.onPrimary,
        containerColor = MaterialTheme.colorScheme.primary,
        edgePadding = 0.dp,
        indicator = {
            TabRowDefaults.Indicator(
                modifier = Modifier.tabIndicatorOffset(it[selectedTabIndex]).offset(y = (-1).dp),
                height = 2.dp,
                color = MaterialTheme.colorScheme.onPrimary
            )
        }
    )
}

@Composable
fun WanTab(
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    text: @Composable () -> Unit
) {
    Tab(selected = selected, onClick = onClick, modifier = modifier, enabled = enabled, text = {
        ProvideTextStyle(value = MaterialTheme.typography.labelLarge.copy(textAlign = TextAlign.Center)) {
            Box() {
                text()
            }
        }
    })
}