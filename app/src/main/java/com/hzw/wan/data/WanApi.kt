package com.hzw.wan.data

import com.hzw.wan.data.dto.*
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

    /**
     * 体系数据
     */
    @GET("tree/json")
    suspend fun getAndroidSystem(): Dto<List<SystemDto>>

    /**
     * 知识体系下的文章
     * https://www.wanandroid.com/article/list/0/json?cid=60&page_size=20
     */
    @GET("article/list/{page}/json")
    suspend fun getSystemArticle(
        @Path("page") page: Int,
        @Query("cid") cid: Int,
        @Query("page_size") page_size: Int
    ): Dto<ArticleListDto>

    /**
     * 项目分类
     * @return
     */
    @GET("project/tree/json")
    suspend fun getProjectCategory(): Dto<List<ProjectCategoryDto>>

    /**
     * 根据分类ID获取分类数据
     * @param page
     * @param cid
     * @return
     */
    @GET("project/list/{page}/json")
    suspend fun getProject(
        @Path("page") page: Int,
        @Query("cid") cid: Int
    ): Dto<ArticleListDto>

}