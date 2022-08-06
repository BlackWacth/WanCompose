package com.hzw.wan.ui.course.chapter

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.google.accompanist.swiperefresh.SwipeRefreshState
import com.hzw.wan.domain.model.Article
import com.hzw.wan.domain.model.Course
import com.hzw.wan.domain.repository.course.CourseRepository
import com.hzw.wan.ui.main.AppRouter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import javax.inject.Inject

@HiltViewModel
class CourseChapterViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val repository: CourseRepository
) : ViewModel() {

    private val course: Course = checkNotNull(
        savedStateHandle[AppRouter.CourseChapter.argCourse]
    )

    var refreshState = SwipeRefreshState(false)

    var listFlow: Flow<PagingData<Article>> = emptyFlow()

    init {
        getCourseChapter()
    }

    private fun getCourseChapter() {
        listFlow = repository.getCourseChapterList(course.id).cachedIn(viewModelScope)
    }
}