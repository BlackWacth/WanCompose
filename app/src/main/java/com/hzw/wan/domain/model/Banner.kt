package com.hzw.wan.domain.model

data class Banner(
    val desc: String,
    val id: Int,
    val imagePath: String,
    val title: String,
    val type: Int,
    val url: String,
    val data: String
)