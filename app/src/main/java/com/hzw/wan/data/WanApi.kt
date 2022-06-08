package com.hzw.wan.data

import com.hzw.wan.data.dto.ArticleListDto
import com.hzw.wan.data.dto.BannerDto
import com.hzw.wan.data.dto.Dto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

const val PAGE_SIZE = 20

interface WanApi {

    companion object {
        const val BASE_URL = "https://www.wanandroid.com/"
    }

    @GET("banner/json")
    suspend fun getBanner(): Dto<List<BannerDto>>

    @GET("article/list/{index}/json")
    suspend fun getArticle(
        @Path("index") index: Int,
        @Query("page_size") pageSize: Int
    ): Dto<ArticleListDto>
}