package com.hzw.wan.domain.repository.project

import androidx.paging.PagingData
import com.hzw.wan.domain.model.Article
import com.hzw.wan.domain.model.ProjectCategory
import kotlinx.coroutines.flow.Flow

interface ProjectRepository {

    fun getProjectCategory(): Flow<List<ProjectCategory>>

    fun getProject(index: Int, cid: Int): Flow<PagingData<Article>>

}