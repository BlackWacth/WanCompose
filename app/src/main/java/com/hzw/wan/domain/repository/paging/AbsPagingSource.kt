package com.hzw.wan.domain.repository.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.hzw.wan.data.dto.ArticleDto
import com.hzw.wan.domain.mapper.toArticle
import com.hzw.wan.domain.model.Article
import com.hzw.wan.extend.logI

abstract class AbsPagingSource : PagingSource<Int, Article>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {
        return try {

            val index = params.key ?: 0
            val list = getArticleList(index).map {
                it.toArticle()
            }
            "load(${params.javaClass.simpleName}) ==> key = ${params.key}, loadSize = ${params.loadSize}, placeholdersEnabled = ${params.placeholdersEnabled}, listSize = ${list.size}".logI()
            val preKey = null
            val nextKey = if (list.isNotEmpty()) index + 1 else null
            LoadResult.Page(list, preKey, nextKey)
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Article>): Int? {
        return null
    }

    abstract suspend fun getArticleList(index: Int): List<ArticleDto>
}