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
class LoginViewModel @Inject constructor(private val accountRepository: AccountRepository) :
    ViewModel() {

    var userName by mutableStateOf("")
    var password by mutableStateOf("")

    val loginStateFlow = MutableStateFlow<AppState<UserInfo>>(AppState.None)

    fun login() {
        if (userName.isBlank() || password.isBlank()) {
            loginStateFlow.value = AppState.Error("账号或密码，不能为空")
            return
        }
        viewModelScope.launch {
            accountRepository.login(userName, password).toAppState().collect {
                loginStateFlow.value = it
            }
        }
    }

    fun recover() {
        loginStateFlow.value = AppState.None
    }
}