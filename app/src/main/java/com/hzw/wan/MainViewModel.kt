package com.hzw.wan

import androidx.lifecycle.ViewModel
import com.hzw.wan.domain.repository.home.HomeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val homeRepository: HomeRepository
) : ViewModel() {
}