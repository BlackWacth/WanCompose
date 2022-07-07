package com.hzw.wan.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

data class AndroidSystem(
    val children: List<AndroidSystemChildren>,
    val id: Int,
    val name: String,
)

@Parcelize
data class AndroidSystemChildren(
    val id: Int,
    val name: String
) : Parcelable