package com.hzw.wan.ui.home

import com.hzw.wan.domain.model.Article
import com.hzw.wan.domain.model.Banner

data class HomeState(
    val bannerList: List<Banner> = emptyList(),
    val articleList: List<Article> = emptyList(),
    val isLoading: Boolean = false
)