package com.chaudharynabin6.newapp.domain.repository

import androidx.lifecycle.LiveData
import com.chaudharynabin6.newapp.data.datasources.local.ArticleEntityLocal
import com.chaudharynabin6.newapp.domain.entity.ArticleEntity
import com.chaudharynabin6.newapp.domain.entity.TitleEntity
import com.chaudharynabin6.newapp.other.utils.Resource
import kotlinx.coroutines.flow.Flow

interface NewsRepository {

    suspend fun getArticles(query : String) : Flow<Resource<List<ArticleEntity>>>

    suspend fun getAllSavedTitles() : Flow<List<TitleEntity>>

    suspend fun insertTitle(titleEntity: TitleEntity)

    suspend fun insertArticles(articleEntityLocals: List<ArticleEntityLocal>)

    suspend fun deleteAllArticles()

    suspend fun getAllArticles() : List<ArticleEntityLocal>
}