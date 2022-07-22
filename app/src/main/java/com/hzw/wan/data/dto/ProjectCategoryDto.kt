package com.hzw.wan.data.dto

data class ProjectCategoryDto(
    val uid: Int,
    val courseId: Int,
    val id: Int,
    val name: String?,
    val order: Int,
    val parentChapterId: Int,
    val userControlSetTop: Boolean,
    val visible: Int
)