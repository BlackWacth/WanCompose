package com.hzw.wan.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Article(
    val audit: Int = 0,
    val author: String = "",
    val desc: String = "",
    val title: String = "",
    val type: Int = 0,
    val pic: String = "",
    val superChapterName: String = "",
    val chapterName: String = "",
    val shareUser: String = "",
    val link: String = "",
) : Parcelable