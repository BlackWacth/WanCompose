package com.hzw.wan.data.dto

data class Dto<T>(
    val data: T?,
    val errorCode: Int,
    val errorMsg: String
) {
    fun isSuccess() = errorCode == 0
}

fun <T> Dto<T>?.checkSuccess(): T? {
    if (this != null && isSuccess()) {
        return data
    } else {
        throw RequestException(code = this?.errorCode ?: -1, message = this?.errorMsg ?: "未知错误")
    }
}

class RequestException(val code: Int = -1, message: String = "", cause: Throwable? = null) :
    Exception(message, cause)