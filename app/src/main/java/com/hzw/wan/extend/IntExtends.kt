package com.hzw.wan.extend

fun Int?.ifNull(default: Int = 0): Int {
    return this ?: default
}