package com.hzw.wan.domain.model

data class AndroidSystem(
    val children: List<AndroidSystemChildren>,
    val id: Int,
    val name: String,
)

data class AndroidSystemChildren(
    val id: Int,
    val name: String
)