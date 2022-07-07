package com.hzw.wan.ui.system.list

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.hzw.wan.domain.model.Article
import com.hzw.wan.domain.repository.system.SystemRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArticleListViewModel @Inject constructor(private val repository: SystemRepository) :
    ViewModel() {

    var refreshState by mutableStateOf(false)

    var listFlow: Flow<PagingData<Article>> = emptyFlow()

    fun getList(id: Int) {
        viewModelScope.launch {
            listFlow = repository.getSystemArticleList(id).cachedIn(viewModelScope)
        }
    }
}

data class SystemArticleState(val cid: Int, val isRefresh: Boolean)