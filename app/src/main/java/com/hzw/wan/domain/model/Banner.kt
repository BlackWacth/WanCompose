package com.hzw.wan.domain.model

import com.zj.banner.model.BaseBannerBean

data class Banner(
    val desc: String,
    val id: Int,
    val imagePath: String,
    val title: String,
    val type: Int,
    val url: String,
    override val data: String
): BaseBannerBean()