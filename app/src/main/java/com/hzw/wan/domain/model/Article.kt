package com.hzw.wan.domain.model

data class Article(
    val audit: Int,
    val author: String,
    val desc: String,
    val title: String,
    val type: Int,
    val pic: String,
    val superChapterName: String,
    val chapterName: String,
    val shareUser: String
)