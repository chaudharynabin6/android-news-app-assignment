package com.chaudharynabin6.newapp.data.repository

import androidx.lifecycle.LiveData
import com.chaudharynabin6.newapp.data.datasources.local.NewsDataBase
import com.chaudharynabin6.newapp.data.datasources.remote.NewsAPI
import com.chaudharynabin6.newapp.data.mapper.toArticleEntity
import com.chaudharynabin6.newapp.data.mapper.toTitleEntity
import com.chaudharynabin6.newapp.data.mapper.toTitleEntityLocal
import com.chaudharynabin6.newapp.domain.entity.ArticleEntity
import com.chaudharynabin6.newapp.domain.entity.TitleEntity
import com.chaudharynabin6.newapp.domain.repository.NewsRepository
import com.chaudharynabin6.newapp.other.utils.Resource
import kotlinx.coroutines.flow.*
import retrofit2.HttpException
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(
    private val newsAPI: NewsAPI,
    private val newsDataBase: NewsDataBase,
) : NewsRepository {

    private val dao = newsDataBase.dao()

    override suspend fun getArticles(query: String): Flow<Resource<List<ArticleEntity>>> {
        return flow {
//            initially loading is set
            emit(Resource.Loading(isLoading = true))

            val newsResponseDto = newsAPI.getNews(q = query)
//
            try {
                val articleEntities = newsResponseDto.articles.mapNotNull {
                    it.toArticleEntity()
                }

                emit(Resource.Success(
                    articleEntities
                ))


            } catch (e: HttpException) {
                e.printStackTrace()
                emit(Resource.Error(
                    message = "cannot get the news"
                ))
            }

            emit(Resource.Loading(isLoading = false))

            return@flow
        }
    }

    override suspend fun getAllSavedTitles(): Flow<List<TitleEntity>> {
        val titleEntityLocals =  dao.getAllTitleSortedByDateSavedDesc()

        return  titleEntityLocals.map {
            listTitleEntityLocal ->
            listTitleEntityLocal.map {
                it.toTitleEntity()
            }
        }
    }

    override suspend fun insertTitle(titleEntity: TitleEntity) {
        dao.insertTitle(titleEntity.toTitleEntityLocal())
    }
}