package com.hzw.wan.ui.course

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.accompanist.swiperefresh.SwipeRefreshState
import com.hzw.wan.domain.Result
import com.hzw.wan.domain.asResult
import com.hzw.wan.domain.model.Course
import com.hzw.wan.domain.repository.course.CourseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CourseViewModel @Inject constructor(private val courseRepository: CourseRepository) :
    ViewModel() {

    var refreshState = SwipeRefreshState(false)

    lateinit var courseStateFlow: StateFlow<Result<List<Course>>>

    init {
        getCourseList()
    }

    fun getCourseList() {
        viewModelScope.launch {
            courseStateFlow = courseRepository.getCourseList()
                .asResult()
                .onStart { refreshState.isRefreshing = true }.onCompletion { refreshState.isRefreshing = false }
                .stateIn(this)
        }
    }
}