package com.hzw.wan.data

import com.hzw.wan.data.dto.ArticleListDto
import com.hzw.wan.data.dto.BannerDto
import com.hzw.wan.data.dto.Dto
import retrofit2.http.GET
import retrofit2.http.Path

interface WanApi {

    companion object {
        const val BASE_URL = "https://www.wanandroid.com/"
    }

    @GET("banner/json")
    suspend fun getBanner(): Dto<List<BannerDto>>

    @GET("article/list/{a}/json")
    suspend fun getArticle(@Path("a") a: Int): Dto<ArticleListDto>
}