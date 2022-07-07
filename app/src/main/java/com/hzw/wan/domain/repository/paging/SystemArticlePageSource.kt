package com.hzw.wan.domain.repository.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.hzw.wan.data.PAGE_SIZE
import com.hzw.wan.data.WanApi
import com.hzw.wan.data.dto.checkSuccess
import com.hzw.wan.domain.mapper.toArticle
import com.hzw.wan.domain.model.Article

/**
 * 体系中的文件列表
 * @property wanApi
 * @property id
 */
class SystemArticlePageSource constructor(private val wanApi: WanApi, val id: Int) :
    PagingSource<Int, Article>() {
    override fun getRefreshKey(state: PagingState<Int, Article>): Int? {
        return state.anchorPosition?.let {
            val page = state.closestPageToPosition(it)
            page?.prevKey?.plus(1) ?: page?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {
        val index = params.key ?: 0
        val list =
            wanApi.getSystemArticle(index, id, PAGE_SIZE).checkSuccess()?.datas?.map { it.toArticle() } ?: listOf()
        val nextKey = if (list.isNotEmpty()) index + 1 else null
        return LoadResult.Page(
            list, prevKey = null, nextKey = nextKey
        )
    }
}