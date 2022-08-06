package com.hzw.wan.domain.repository.project

import androidx.paging.*
import com.hzw.wan.data.PAGE_SIZE
import com.hzw.wan.data.WanApi
import com.hzw.wan.data.dto.checkSuccess
import com.hzw.wan.domain.mapper.toArticle
import com.hzw.wan.domain.mapper.toProjectCategory
import com.hzw.wan.domain.model.Article
import com.hzw.wan.domain.model.ProjectCategory
import com.hzw.wan.domain.repository.paging.createPagingSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

/**
 * ProjectRepositoryImpl
 * 项目数据仓库
 * @property wanApi
 */
@Singleton
class ProjectRepositoryImpl @Inject constructor(private val wanApi: WanApi) : ProjectRepository {

    override fun getProjectCategory(): Flow<List<ProjectCategory>> {
        return flow {
            val dto = wanApi.getProjectCategory()
            if (dto.isSuccess() && !dto.data.isNullOrEmpty()) {
                val list = dto.data.map { it.toProjectCategory() }
                emit(list)
            } else {
                emit(emptyList())
            }
        }
    }

    override fun getProject(cid: Int): Flow<PagingData<Article>> {
        return Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                enablePlaceholders = true,
                initialLoadSize = PAGE_SIZE
            )
        ) {
            createPagingSource { index ->
                wanApi.getProject(index, cid).checkSuccess()?.datas?.map { it.toArticle() } ?: emptyList()
            }
        }.flow
    }
}