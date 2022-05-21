package com.hzw.wan.data.dto

data class ArticleListDto(
    val curPage: Int,
    val datas: List<ArticleDto>,
    val offset: Int,
    val over: Boolean,
    val pageCount: Int,
    val size: Int,
    val total: Int
)

data class ArticleDto(
    val uid: Int,
    val apkLink: String?,
    val audit: Int,
    val author: String?,
    val canEdit: Boolean,
    val chapterId: Int,
    val chapterName: String,
    var collect: Boolean,
    val courseId: Int,
    var desc: String?,
    val descMd: String?,
    val envelopePic: String?,
    val fresh: Boolean,
    val id: Int,
    val link: String? = "",
    val niceDate: String? = "",
    val niceShareDate: String? = "",
    val origin: String? = "",
    val prefix: String? = "",
    val projectLink: String? = "",
    val publishTime: Long = 0L,
    val selfVisible: Int = 0,
    val shareDate: Long = 0L,
    val shareUser: String? = "",
    val superChapterId: Int = 0,
    val superChapterName: String? = "",
    var title: String? = "",
    val type: Int = 0,
    val userId: Int = 0,
    val visible: Int = 0,
    val zan: Int = 0,
    var localType: Int = 0
)