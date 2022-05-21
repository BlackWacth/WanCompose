package com.hzw.wan.extend

fun String?.ifNullOrBlank(default: String = ""): String {
    return if (this.isNullOrBlank()) {
        default
    } else {
        this
    }
}