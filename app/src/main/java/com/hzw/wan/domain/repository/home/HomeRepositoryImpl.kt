package com.hzw.wan.domain.repository.home

import com.hzw.wan.data.WanApi
import com.hzw.wan.data.dto.checkSuccess
import com.hzw.wan.domain.mapper.toArticle
import com.hzw.wan.domain.mapper.toBanner
import com.hzw.wan.domain.model.Article
import com.hzw.wan.domain.model.Banner
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HomeRepositoryImpl @Inject constructor(
    private val wanApi: WanApi
) : HomeRepository {

    override suspend fun getBanner(): List<Banner> {
        return try {
            wanApi.getBanner().checkSuccess()?.map {
                it.toBanner()
            } ?: listOf()
        } catch (e: Exception) {
            e.printStackTrace()
            listOf()
        }
    }

    override suspend fun getArticle(index: Int): List<Article> {
        return try {
            wanApi.getArticle(index).checkSuccess()?.datas?.map {
                it.toArticle()
            } ?: listOf()
        } catch (e: Exception) {
            e.printStackTrace()
            listOf()
        }
    }
}