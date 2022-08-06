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
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProjectViewModel @Inject constructor(private val repository: ProjectRepository) :
    ViewModel() {

    private val _tabState: MutableStateFlow<ProjectTabState> = MutableStateFlow(
        ProjectTabState.Loading
    )
    val tabState: StateFlow<ProjectTabState> = _tabState.asStateFlow()

    fun switchTab(newIndex: Int) {
        val tab = tabState.value
        if (tab is ProjectTabState.Tabs) {
            if (newIndex != tab.currentIndex) {
                _tabState.update { state ->
                    val tabs = tab.tabs
                    if (newIndex in tabs.indices) {
                        getProjectContent(tabs[newIndex].id)
                        tab.copy(currentIndex = newIndex)
                    } else {
                        tab.copy(currentIndex = 0)
                    }
                }
            }
        }
    }

    init {
        viewModelScope.launch {
            repository.getProjectCategory().asResult().map { result ->
                when (result) {
                    is Result.Loading -> {
                        ProjectTabState.Loading
                    }
                    is Result.Error -> {
                        ProjectTabState.Error
                    }
                    is Result.Success<List<ProjectCategory>> -> {
                        if (result.data.isEmpty()) {
                            ProjectTabState.Empty
                        } else {
                            ProjectTabState.Tabs(result.data, 0)
                        }
                    }
                }
            }.collect { state ->
                _tabState.value = state
                if (state is ProjectTabState.Tabs) {
                    getProjectContent(state.tabs.first().id)
                }
            }
        }
    }

    var contentFlow: Flow<PagingData<Article>> = emptyFlow()
    private fun getProjectContent(id: Int) {
        contentFlow = repository.getProject(id).cachedIn(viewModelScope)
    }
}

sealed interface ProjectTabState {
    object Loading : ProjectTabState
    object Error : ProjectTabState
    object Empty : ProjectTabState
    data class Tabs(
        val tabs: List<ProjectCategory>,
        val currentIndex: Int
    ) : ProjectTabState
}

sealed interface ProjectContentState {

    object Loading : ProjectContentState

    data class Content(
        val pagingData: Flow<PagingData<Article>>
    ) : ProjectContentState

    object Error : ProjectContentState
    object Empty : ProjectContentState
}

