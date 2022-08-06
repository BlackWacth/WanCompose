package com.hzw.wan.domain.repository.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState

fun <T : Any> createPagingSource(dataBlock: suspend (index: Int) -> List<T>): PagingSource<Int, T> {
    return object : PagingSource<Int, T>() {
        override fun getRefreshKey(state: PagingState<Int, T>): Int? {
            return state.anchorPosition?.let {
                val page = state.closestPageToPosition(it)
                page?.prevKey?.plus(1) ?: page?.nextKey?.minus(1)
            }
        }

        override suspend fun load(params: LoadParams<Int>): LoadResult<Int, T> {
            val index = params.key ?: 0
            val list = dataBlock(index)
            val nextKey = if (list.isNotEmpty()) index + 1 else null
            return LoadResult.Page(
                list, prevKey = null, nextKey = nextKey
            )
        }
    }
}