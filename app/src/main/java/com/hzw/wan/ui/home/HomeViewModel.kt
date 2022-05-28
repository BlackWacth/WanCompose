package com.hzw.wan.ui.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hzw.wan.domain.repository.home.HomeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: HomeRepository) : ViewModel() {

    var state by mutableStateOf(HomeState())

    fun getHomeData(index: Int) {
        viewModelScope.launch {
            state = state.copy(isLoading = true)
            if (index == 0) {
                val bannerDeferred = viewModelScope.async { repository.getBanner() }
                val articleDeferred = viewModelScope.async { repository.getArticle(index) }
                state = state.copy(
                    bannerList = bannerDeferred.await(),
                    articleList = articleDeferred.await(),
                    isLoading = false
                )
            } else {
                val articleList = repository.getArticle(index)
                state = state.copy(
                    bannerList = emptyList(),
                    articleList = articleList,
                    isLoading = false
                )
            }
        }
    }
}