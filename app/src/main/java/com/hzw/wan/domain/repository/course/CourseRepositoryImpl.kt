package com.hzw.wan.domain.repository.course

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.hzw.wan.data.PAGE_SIZE
import com.hzw.wan.data.WanApi
import com.hzw.wan.data.dto.checkSuccess
import com.hzw.wan.domain.mapper.toArticle
import com.hzw.wan.domain.mapper.toCourse
import com.hzw.wan.domain.model.Article
import com.hzw.wan.domain.model.Course
import com.hzw.wan.domain.repository.paging.createPagingSource
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

/**
 * 课程
 * @property api
 */
@Singleton
class CourseRepositoryImpl @Inject constructor(private val api: WanApi) : CourseRepository {
    override fun getCourseList(): Flow<List<Course>> {
        return flow {
            emit(api.getCourseList().data?.map { it.toCourse() } ?: emptyList())
        }
    }

    override fun getCourseChapterList(cid: Int): Flow<PagingData<Article>> {
        return Pager(config = PagingConfig(pageSize = PAGE_SIZE)) {
            createPagingSource { index ->
                api.getCourseChapterList(cid = cid, index = index, pageSize = PAGE_SIZE)
                    .checkSuccess()?.datas?.map { it.toArticle() } ?: emptyList()
            }
        }.flow
    }
}