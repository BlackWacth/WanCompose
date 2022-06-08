package com.hzw.wan.extend

import android.text.Html

fun String?.ifNullOrBlank(default: String = ""): String {
    return if (this.isNullOrBlank()) {
        default
    } else {
        this
    }
}

/**
 * 将Html文本转为普通字符串
 * @return
 */
fun String?.parseHtmlToText(): String {
    return Html.fromHtml(this.ifNullOrBlank(), Html.FROM_HTML_MODE_LEGACY).toString()
}