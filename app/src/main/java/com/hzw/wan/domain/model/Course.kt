package com.hzw.wan.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Course(
    val id: Int,
    val name: String,
    val author: String,
    val cover: String,
    val desc: String
): Parcelable
