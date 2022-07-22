package com.hzw.wan.domain.mapper

import com.hzw.wan.data.dto.ProjectCategoryDto
import com.hzw.wan.domain.model.ProjectCategory
import com.hzw.wan.extend.ifNullOrBlank

fun ProjectCategoryDto.toProjectCategory(): ProjectCategory {
    return ProjectCategory(this.id, this.name.ifNullOrBlank())
}