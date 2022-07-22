package com.hzw.wan.ui.system.list

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.hzw.wan.domain.model.AndroidSystemChildren
import com.hzw.wan.domain.model.Article
import com.hzw.wan.domain.repository.system.SystemRepository
import com.hzw.wan.ui.main.AppRouter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArticleListViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val repository: SystemRepository
) : ViewModel() {

    private val child: AndroidSystemChildren = checkNotNull(
        savedStateHandle[AppRouter.SystemArticleList.argumentSystemChild]
    )

    var refreshState by mutableStateOf(false)

    var listFlow: Flow<PagingData<Article>> = emptyFlow()

    init {
        getList()
    }

    fun getList() {
        viewModelScope.launch {
            listFlow = repository.getSystemArticleList(child.id).cachedIn(viewModelScope)
        }
    }
}