package com.hzw.wan.data

import com.hzw.wan.data.dto.*
import okhttp3.RequestBody
import retrofit2.http.*

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

    /**
     * 获取课程列表
     * @return
     */
    @GET("chapter/547/sublist/json")
    suspend fun getCourseList(): Dto<List<CourseDto>>

    @GET("article/list/{index}/json?order_type=1")
    suspend fun getCourseChapterList(
        @Path("index") index: Int,
        @Query("cid") cid: Int,
        @Query("page_size") pageSize: Int
    ): Dto<ArticleListDto>

    @POST("user/login")
    suspend fun login(
        @Query("username") username: String,
        @Query("password") password: String
    ): Dto<LoginDto>

    @POST("user/register")
    suspend fun register(
        @Query("username") username: String,
        @Query("password") password: String,
        @Query("repassword") repassword: String
    ): Dto<LoginDto>

    @GET("user/logout/json")
    suspend fun getLogout(): Dto<Any>

}