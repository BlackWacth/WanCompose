package com.hzw.wan.domain.mapper

import com.hzw.wan.data.dto.SystemChildrenDto
import com.hzw.wan.data.dto.SystemDto
import com.hzw.wan.domain.model.AndroidSystem
import com.hzw.wan.domain.model.AndroidSystemChildren

fun SystemChildrenDto.toAndroidSystemChildren(): AndroidSystemChildren {
    return AndroidSystemChildren(id, name)
}

fun SystemDto.toAndroidSystem(): AndroidSystem {
    val newChildren = children?.map { it.toAndroidSystemChildren() } ?: arrayListOf()
    return AndroidSystem(newChildren, id, name)
}