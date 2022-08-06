package com.hzw.wan.domain.repository.course

import androidx.paging.PagingData
import com.hzw.wan.domain.model.Article
import com.hzw.wan.domain.model.Course
import kotlinx.coroutines.flow.Flow

interface CourseRepository {

    fun getCourseList(): Flow<List<Course>>

    fun getCourseChapterList(cid: Int): Flow<PagingData<Article>>
}