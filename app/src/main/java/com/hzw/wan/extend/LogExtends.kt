package com.hzw.wan.extend

import android.util.Log

private const val MAX_LOG_LENGTH = 4000
private const val MAX_TAG_LENGTH = 23
var isPrintLog = true

private fun getTag(): String {
    val trace = Throwable().stackTrace.first {
        !it.className.contains("LogExtendsKt") //判处栈中当前文件的信息
    } ?: return ""
    val fullTag = trace.toString()
    val className = trace.className.substringAfterLast('.')
    val tag = fullTag.subSequence(fullTag.indexOf(className), fullTag.length).toString()
    return if (tag.length <= MAX_TAG_LENGTH) {
        tag
    } else {
        className
    }
}

private fun printLog(priority: Int, tag: String, msg: String) {
    if (!isPrintLog) {
        return
    }
    if (msg.length < MAX_LOG_LENGTH) {
        if (priority == Log.ASSERT) {
            Log.wtf(tag, msg)
        } else {
            Log.println(priority, tag, msg)
        }
        return
    }
    // 按行分割，然后确保每行都可以适合Log的最大长度。
    var i = 0
    val length = msg.length
    while (i < length) {
        var newline = msg.indexOf('\n', i)
        newline = if (newline != -1) newline else length
        do {
            val end = newline.coerceAtMost(i + MAX_LOG_LENGTH)
            val part = msg.substring(i, end)
            if (priority == Log.ASSERT) {
                Log.wtf(tag, part)
            } else {
                Log.println(priority, tag, part)
            }
            i = end
        } while (i < newline)
        i++
    }
}

fun String?.logV(tag: String = "") {
    if (this.isNullOrEmpty()) {
        return
    }
    val newTag = tag.ifEmpty {
        getTag()
    }
    printLog(Log.VERBOSE, newTag, this)
}

fun String?.logD(tag: String = "") {
    if (this.isNullOrEmpty()) {
        return
    }
    val newTag = tag.ifEmpty {
        getTag()
    }
    printLog(Log.DEBUG, newTag, this)
}

fun String?.logI(tag: String = "") {
    if (this.isNullOrEmpty()) {
        return
    }
    val newTag = tag.ifEmpty {
        getTag()
    }
    printLog(Log.INFO, newTag, this)
}

fun String?.logW(tag: String = "", throwable: Throwable? = null) {
    if (this.isNullOrEmpty()) {
        return
    }
    val newTag = tag.ifEmpty {
        getTag()
    }
    val msg = "$this \n ${Log.getStackTraceString(throwable)}"
    printLog(Log.WARN, newTag, this)
}

fun String?.logE(tag: String = "", throwable: Throwable? = null) {
    if (this.isNullOrEmpty()) {
        return
    }
    val newTag = tag.ifEmpty {
        getTag()
    }
    val msg = "$this \n ${Log.getStackTraceString(throwable)}"
    printLog(Log.ERROR, newTag, msg)
}

fun Throwable?.logE(tag: String = "") {
    if (this == null) {
        return
    }
    val newTag = tag.ifEmpty {
        getTag()
    }
    val msg = Log.getStackTraceString(this)
    printLog(Log.ERROR, newTag, msg)
}

fun String?.logA(tag: String = "", throwable: Throwable? = null) {
    if (this.isNullOrEmpty()) {
        return
    }
    val newTag = tag.ifEmpty {
        getTag()
    }
    val msg = "$this \n ${Log.getStackTraceString(throwable)}"
    printLog(Log.ASSERT, newTag, msg)
}



