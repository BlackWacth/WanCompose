package com.hzw.wan.ui.project

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.hzw.wan.domain.Result
import com.hzw.wan.domain.asResult
import com.hzw.wan.domain.model.Article
import com.hzw.wan.domain.model.ProjectCategory
import com.hzw.wan.domain.repository.project.ProjectRepository
import com.hzw.wan.ui.main.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProjectViewModel @Inject constructor(private val repository: ProjectRepository) :
    ViewModel() {

    private val _tabState: MutableStateFlow<ProjectTabState> = MutableStateFlow(
        ProjectTabState(emptyList(), 0)
    )

    val tabState: StateFlow<ProjectTabState> = _tabState.asStateFlow()

    fun switchTab(newIndex: Int) {
        if (newIndex != tabState.value.currentIndex) {
            _tabState.update { state ->
                val tabs = tabState.value.tabs
                if (newIndex in tabs.indices) {
                    getProjectContent(tabs[newIndex].id)
                    state.copy(currentIndex = newIndex)
                } else {
                    state.copy(currentIndex = 0)
                }
            }
        }
    }

    var contentState: StateFlow<ProjectContentState>

    init {
        contentState = repository.getProjectCategory().asResult().map { result ->
            when (result) {
                is Result.Loading -> {
                    ProjectContentState.Loading
                }
                is Result.Error -> {
                    ProjectContentState.Error
                }
                is Result.Success<List<ProjectCategory>> -> {
                    if (result.data.isEmpty()) {
                        ProjectContentState.Error
                    } else {
                        val flow = repository.getProject(0, result.data.first().id)
                            .cachedIn(viewModelScope)
                        ProjectContentState.Content(flow)
                    }
                }
            }
        }.stateIn(
            viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = ProjectContentState.Loading
        )
    }

    private fun getProjectContent(id: Int) {
//        contentState = repository.getProject(0, id).cachedIn(viewModelScope).stateIn(
//            viewModelScope,
//            started = SharingStarted.WhileSubscribed(5000),
//            initialValue = ProjectContentState.Loading
//        )
    }
}

data class ProjectTabState(
    val tabs: List<ProjectCategory>,
    val currentIndex: Int
)

sealed interface ProjectContentState {

    object Loading : ProjectContentState

    data class Content(
        val pagingData: Flow<PagingData<Article>>
    ) : ProjectContentState

    object Error : ProjectContentState
}

