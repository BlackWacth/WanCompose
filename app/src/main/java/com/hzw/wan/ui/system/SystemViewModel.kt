package com.hzw.wan.ui.system

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hzw.wan.domain.Resource
import com.hzw.wan.domain.model.AndroidSystem
import com.hzw.wan.domain.repository.system.SystemRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SystemViewModel @Inject constructor(private val repository: SystemRepository) : ViewModel() {

    private val androidSystemLiveData = MutableLiveData<Resource<List<AndroidSystem>>>()
    val liveData: LiveData<Resource<List<AndroidSystem>>> = androidSystemLiveData

    val selectedSystemState = mutableStateOf(AndroidSystem(listOf(), -1, ""))

    init {
        viewModelScope.launch {
            repository.getSystem().collect {
                if (!it.data.isNullOrEmpty()) {
                    selectedSystemState.value = it.data.first()
                }
                androidSystemLiveData.value = it
            }
        }
    }
}