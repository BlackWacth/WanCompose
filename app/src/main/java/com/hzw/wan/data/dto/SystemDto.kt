package com.hzw.wan.data.dto

data class SystemDto(
    val children: ArrayList<SystemChildrenDto>?,
    val courseId: Int = 0,
    val id: Int = 0,
    val name: String = "",
    val order: Int = 0,
    val parentChapterId: Int = 0,
    val userControlSetTop: Boolean = false,
    val visible: Int = 0
)
data class SystemChildrenDto(
    val children: List<Any>,
    val courseId: Int,
    val id: Int,
    val name: String,
    val order: Int,
    val parentChapterId: Int,
    val userControlSetTop: Boolean,
    val visible: Int
)