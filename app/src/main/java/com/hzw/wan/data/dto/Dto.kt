package com.hzw.wan.data.dto

data class Dto<T>(
    val data: T?,
    val errorCode: Int,
    val errorMsg: String
)