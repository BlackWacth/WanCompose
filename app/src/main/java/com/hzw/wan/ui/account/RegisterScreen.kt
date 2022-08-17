package com.hzw.wan.ui.account

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.hzw.wan.domain.AppState
import com.hzw.wan.extend.logI
import com.hzw.wan.setNavigationBarAndSystemBar

@Composable
fun RegisterScreen(navController: NavController) {
    val viewModel: RegisterViewModel = hiltViewModel()
    val state by viewModel.stateFlow.collectAsState()
    val focusManager = LocalFocusManager.current
    setNavigationBarAndSystemBar(
        navigationColor = MaterialTheme.colorScheme.background,
        systemBarColor = MaterialTheme.colorScheme.background,
        darkIcons = true
    )
    when (state) {
        is AppState.Error -> {
            val message = (state as AppState.Error).message ?: "未知错误"
            "message = $message".logI()
            message.toast(LocalContext.current)
            viewModel.recover()
        }

        is AppState.Success -> {
            "登录成功".toast(LocalContext.current)
            viewModel.recover()
            navController.popBackStack()
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background)
            .clickable { focusManager.clearFocus() }
    ) {
        Text(
            text = "注册",
            fontSize = 50.sp,
            modifier = Modifier.padding(top = 30.dp, start = 10.dp),
            color = MaterialTheme.colorScheme.primary
        )

        InputTextField(
            value = viewModel.userName, onValueChange = {
                viewModel.userName = it
            },
            hint = "请输入用户名",
            modifier = Modifier.padding(top = 20.dp)
        )
        InputTextField(
            value = viewModel.password,
            onValueChange = {
                viewModel.password = it
            },
            hint = "请输入密码",
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            modifier = Modifier.padding(top = 10.dp)
        )

        InputTextField(
            value = viewModel.rePassword,
            onValueChange = {
                viewModel.rePassword = it
            },
            hint = "请再次输入密码",
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            modifier = Modifier.padding(top = 10.dp)
        )

        LoadingButton(text = "注册", isLoading = state is AppState.Loading) {
            viewModel.register()
            focusManager.clearFocus()
        }
    }
}