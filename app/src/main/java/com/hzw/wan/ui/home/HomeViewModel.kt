package com.hzw.wan.ui.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.hzw.wan.domain.model.Article
import com.hzw.wan.domain.model.Banner
import com.hzw.wan.domain.repository.home.HomeRepository
import com.hzw.wan.extend.logI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: HomeRepository) : ViewModel() {

    var refreshState by mutableStateOf(false)

    var bannerState by mutableStateOf(listOf<Banner>())
        private set

    var articleListFlow: Flow<PagingData<Article>> = emptyFlow()

    init {
        getBannerList()
        getArticleList(0)
    }

    fun getBannerList() {
        viewModelScope.launch {
            bannerState = repository.getBanner()
        }
    }

    fun getArticleList(index: Int) {
        viewModelScope.launch {
            articleListFlow = repository.getArticleList(index).cachedIn(viewModelScope)
        }
    }
}