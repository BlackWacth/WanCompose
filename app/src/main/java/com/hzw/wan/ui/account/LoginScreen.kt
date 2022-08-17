package com.hzw.wan.ui.account

import android.annotation.SuppressLint
import android.content.Context
import android.view.Gravity
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedTextField
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.hzw.wan.domain.AppState
import com.hzw.wan.extend.logI
import com.hzw.wan.setNavigationBarAndSystemBar
import com.hzw.wan.ui.main.enterRegister

@Composable
fun LoginScreen(navController: NavController) {
    val viewModel: LoginViewModel = hiltViewModel()
    setNavigationBarAndSystemBar(
        navigationColor = MaterialTheme.colorScheme.background,
        systemBarColor = MaterialTheme.colorScheme.background,
        darkIcons = true
    )
    val resultState by viewModel.loginStateFlow.collectAsState()
    when (resultState) {
        is AppState.Error -> {
            val message = (resultState as AppState.Error).message ?: "未知错误"
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
    val focusManager = LocalFocusManager.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background)
            .clickable { focusManager.clearFocus() }
    ) {
        Text(
            text = "登录",
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

        LoadingButton(text = "登录", isLoading = resultState is AppState.Loading) {
            viewModel.login()
            focusManager.clearFocus()
        }

        Text(
            text = "没有账号？移步注册",
            modifier = Modifier
                .padding(top = 20.dp, end = 16.dp)
                .align(Alignment.End)
                .clickable { navController.enterRegister() },
            color = MaterialTheme.colorScheme.outline,
            fontSize = 12.sp,
        )
    }
}

@Composable
fun LoadingButton(
    text: String,
    isLoading: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .padding(top = 20.dp)
            .padding(horizontal = 16.dp)
            .fillMaxWidth()
    ) {
        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.size(20.dp),
                color = MaterialTheme.colorScheme.onPrimary, strokeWidth = 1.dp
            )
        } else {
            Text(text = text)
        }
    }
}

@Composable
fun InputTextField(
    value: String,
    onValueChange: (String) -> Unit,
    hint: String,
    modifier: Modifier = Modifier,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = {
            Text(
                text = hint,
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.outline
            )
        },
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        singleLine = true,
        visualTransformation = visualTransformation,
        keyboardOptions = keyboardOptions,
    )
}

@SuppressLint("ShowToast")
fun String?.toast(context: Context) {
    if (this.isNullOrEmpty()) {
        return
    }
    val toast = Toast.makeText(context, this, Toast.LENGTH_SHORT)
    toast.setGravity(Gravity.TOP, 0, 100)
    toast.show()
}