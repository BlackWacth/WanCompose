package com.hzw.wan.domain.repository.course

import com.hzw.wan.domain.model.Course
import kotlinx.coroutines.flow.Flow

interface CourseRepository {

    suspend fun getCourseList(): Flow<List<Course>>

}