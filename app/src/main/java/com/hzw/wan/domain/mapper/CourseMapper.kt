package com.hzw.wan.domain.mapper

import com.hzw.wan.data.dto.CourseDto
import com.hzw.wan.domain.model.Course
import com.hzw.wan.extend.ifNullOrBlank

fun CourseDto.toCourse(): Course {
    return Course(
        id = id,
        name = name.ifNullOrBlank(),
        author = author.ifNullOrBlank(),
        cover = cover.ifNullOrBlank(),
        desc = desc.ifNullOrBlank()
    )
}