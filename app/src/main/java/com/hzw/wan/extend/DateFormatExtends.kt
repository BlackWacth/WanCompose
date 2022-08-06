package com.hzw.wan.extend

import java.text.SimpleDateFormat
import java.util.*

object DateFormatUtil {

    const val DATE_FORMAT_yyyy_MM_dd_HH_mm_ss = "yyyy-MM-dd HH:mm:ss"
    const val DATE_FORMAT_yyyy_MM_dd_HH_mm = "yyyy-MM-dd HH:mm"
    const val DATE_FORMAT_HH_mm = "HH:mm"

    private val DATE_THREAD_LOCAL by lazy {
        object : ThreadLocal<MutableMap<String, SimpleDateFormat>>() {
            override fun initialValue(): MutableMap<String, SimpleDateFormat> {
                return mutableMapOf()
            }
        }
    }

    fun getDateFormat(pattern: String): SimpleDateFormat {
        val sdfMap = DATE_THREAD_LOCAL.get()!!
        var format = sdfMap[pattern]
        if (format == null) {
            format = SimpleDateFormat(pattern, Locale.CHINA)
            sdfMap[pattern] = format
        }
        return format
    }
}

fun Long?.millis2String(pattern: String): String {
    if (this == null) {
        return ""
    }
    return try {
        DateFormatUtil.getDateFormat(pattern).format(this)
    } catch (e: Exception) {
        ""
    }
}

fun Long?.millis2String(): String = millis2String(DateFormatUtil.DATE_FORMAT_yyyy_MM_dd_HH_mm_ss)

fun Long?.millis2HHmm(): String = millis2String(DateFormatUtil.DATE_FORMAT_HH_mm)

fun Long?.second2String(pattern: String): String {
    if (this == null) {
        return ""
    }
    return try {
        DateFormatUtil.getDateFormat(pattern).format(this * 1000)
    } catch (e: Exception) {
        ""
    }
}

fun Long?.second2String(): String = second2String(DateFormatUtil.DATE_FORMAT_yyyy_MM_dd_HH_mm_ss)

fun String?.string2Millis(pattern: String): Long {
    if (this.isNullOrBlank()) {
        return 0L
    }
    return try {
        DateFormatUtil.getDateFormat(pattern).parse(this)!!.time
    } catch (e: Exception) {
        0L
    }
}

fun String?.string2Millis(): Long = string2Millis(DateFormatUtil.DATE_FORMAT_yyyy_MM_dd_HH_mm_ss)


fun String?.string2Date(pattern: String): Date {
    if (this.isNullOrBlank()) {
        return Date()
    }
    return try {
        DateFormatUtil.getDateFormat(pattern).parse(this)!!
    } catch (e: Exception) {
        Date()
    }
}

fun String?.string2Date(): Date = string2Date(DateFormatUtil.DATE_FORMAT_yyyy_MM_dd_HH_mm_ss)
