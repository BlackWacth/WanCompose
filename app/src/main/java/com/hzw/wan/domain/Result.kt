package com.hzw.wan.domain

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart

sealed interface Result<out T> {
    data class Success<T>(val data: T) : Result<T>

    data class Error(val message: String?, val throwable: Throwable?) : Result<Nothing>

    object Loading : Result<Nothing>

    object None : Result<Nothing>
}

fun <T> Flow<T>.asResult(): Flow<Result<T>> {
    return this.map<T, Result<T>> {
        Result.Success(it)
    }.onStart {
        emit(Result.Loading)
    }.catch {
        emit(Result.Error(it.message, throwable = it))
    }
}

sealed interface AppState<out T> {
    data class Success<T>(val data: T) : AppState<T>

    data class Error(val message: String? = null, val throwable: Throwable? = null) :
        AppState<Nothing>

    object Loading : AppState<Nothing>

    object None : AppState<Nothing>
}

fun <T> Flow<T>.toAppState(): Flow<AppState<T>> {
    return this.map<T, AppState<T>> {
        AppState.Success(it)
    }.onStart {
        emit(AppState.Loading)
    }.catch {
        emit(AppState.Error(it.message, it))
    }
}