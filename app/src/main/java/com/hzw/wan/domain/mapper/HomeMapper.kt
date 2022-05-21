package com.hzw.wan.domain.mapper

import com.hzw.wan.data.dto.ArticleDto
import com.hzw.wan.data.dto.BannerDto
import com.hzw.wan.domain.model.Article
import com.hzw.wan.domain.model.Banner
import com.hzw.wan.extend.ifNull
import com.hzw.wan.extend.ifNullOrBlank

fun BannerDto.toBanner(): Banner {
    return Banner(
        desc = desc.ifNullOrBlank(),
        id = id.ifNull(),
        imagePath = imagePath.ifNullOrBlank(),
        title = title.ifNullOrBlank(),
        type = type.ifNull(),
        url = url.ifNullOrBlank(),
        data = data.ifNullOrBlank()
    )
}

fun ArticleDto.toArticle(): Article {
    return Article(
        audit = audit,
        author = author.ifNullOrBlank(),
        desc = desc.ifNullOrBlank(),
        title = title.ifNullOrBlank(),
        type = type
    )
}