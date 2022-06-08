package com.hzw.wan.domain.mapper

import com.hzw.wan.data.dto.ArticleDto
import com.hzw.wan.data.dto.BannerDto
import com.hzw.wan.domain.model.Article
import com.hzw.wan.domain.model.Banner
import com.hzw.wan.extend.ifNull
import com.hzw.wan.extend.ifNullOrBlank
import com.hzw.wan.extend.parseHtmlToText

fun BannerDto.toBanner(): Banner {
    return Banner(
        desc = desc.ifNullOrBlank(),
        id = id.ifNull(),
        imagePath = imagePath.ifNullOrBlank(),
        title = title.ifNullOrBlank(),
        type = type.ifNull(),
        url = url.ifNullOrBlank(),
        data = imagePath.ifNullOrBlank()
    )
}

fun ArticleDto.toArticle(): Article {
    return Article(
        audit = audit,
        author = author.ifNullOrBlank(),
        desc = desc.ifNullOrBlank(),
        title = title.parseHtmlToText(),
        type = type,
        pic = envelopePic.ifNullOrBlank(),
        superChapterName = superChapterName.ifNullOrBlank(),
        chapterName = chapterName.ifNullOrBlank(),
        shareUser = shareUser.ifNullOrBlank(),
        link = link ?: "www.baidu.com"
    )
}