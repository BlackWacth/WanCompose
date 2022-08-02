package com.hzw.wan.domain.repository.course

import com.hzw.wan.data.WanApi
import com.hzw.wan.domain.mapper.toCourse
import com.hzw.wan.domain.model.Course
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

/**
 * 课程
 * @property api
 */
@Singleton
class CourseRepositoryImpl @Inject constructor(private val api: WanApi): CourseRepository {
    override suspend fun getCourseList(): Flow<List<Course>> {
        return flow {
            emit(api.getCourseList().data?.map { it.toCourse() } ?: emptyList())
        }
    }
}