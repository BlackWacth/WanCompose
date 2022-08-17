package com.hzw.wan.ui.account

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hzw.wan.domain.AppState
import com.hzw.wan.domain.model.UserInfo
import com.hzw.wan.domain.repository.account.AccountRepository
import com.hzw.wan.domain.toAppState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(private val accountRepository: AccountRepository) :
    ViewModel() {

    var userName by mutableStateOf("")
    var password by mutableStateOf("")
    var rePassword by mutableStateOf("")

    val stateFlow = MutableStateFlow<AppState<UserInfo>>(AppState.None)

    fun register() {
        if (userName.isBlank() || password.isBlank() || rePassword.isBlank()) {
            stateFlow.value = AppState.Error("账号或密码，不能为空")
            return
        }
        if (password != rePassword) {
            stateFlow.value = AppState.Error("密码不一致")
        }
        viewModelScope.launch {
            accountRepository.register(userName, password, rePassword).toAppState().collect {
                stateFlow.value = it
            }
        }
    }

    fun recover() {
        stateFlow.value = AppState.None
    }
}