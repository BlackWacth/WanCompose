package com.hzw.wan.domain.repository.paging

import com.hzw.wan.data.PAGE_SIZE
import com.hzw.wan.data.WanApi
import com.hzw.wan.data.dto.ArticleDto
import com.hzw.wan.data.dto.checkSuccess
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HomePageSource @Inject constructor(private val wanApi: WanApi) : AbsPagingSource() {
    override suspend fun getArticleList(index: Int): List<ArticleDto> {
        return wanApi.getArticle(index, PAGE_SIZE).checkSuccess()?.datas ?: listOf()
    }
}