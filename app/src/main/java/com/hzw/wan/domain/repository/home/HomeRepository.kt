package com.hzw.wan.domain.repository.home

import com.hzw.wan.domain.model.Article
import com.hzw.wan.domain.model.Banner

interface HomeRepository {

    suspend fun getBanner(): List<Banner>

    suspend fun getArticle(index: Int): List<Article>

}