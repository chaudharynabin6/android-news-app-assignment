package com.chaudharynabin6.newapp.data.repository

import com.chaudharynabin6.newapp.data.datasources.local.ArticleEntityLocal
import com.chaudharynabin6.newapp.data.datasources.local.NewsDataBase
import com.chaudharynabin6.newapp.data.datasources.remote.NewsAPI
import com.chaudharynabin6.newapp.data.mapper.toArticleEntity
import com.chaudharynabin6.newapp.data.mapper.toTitleEntity
import com.chaudharynabin6.newapp.data.mapper.toTitleEntityLocal
import com.chaudharynabin6.newapp.domain.entity.ArticleEntity
import com.chaudharynabin6.newapp.domain.entity.TitleEntity
import com.chaudharynabin6.newapp.domain.repository.NewsRepository
import com.chaudharynabin6.newapp.other.utils.Resource
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import retrofit2.HttpException
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(
    private val newsAPI: NewsAPI,
    private val newsDataBase: NewsDataBase,
) : NewsRepository {

    private val titleDao = newsDataBase.titleDao()
    private val articleDao = newsDataBase.articleDao()

    override suspend fun getArticles(query: String): Flow<Resource<List<ArticleEntity>>> {

        return flow {
//            initially loading is set
            emit(Resource.Loading(isLoading = true))


//
            try {
                val newsResponseDto = newsAPI.getNews(q = query)
                val articleEntities = newsResponseDto.articles?.mapNotNull {
                    it.toArticleEntity()
                }

                emit(Resource.Success(
                    articleEntities
                ))
            }
//            catch (e : HttpException){
//                e.printStackTrace()
//                emit(Resource.Error(
//                    message = "network error"
//                ))
//            }
            catch (e: Exception) {
                e.printStackTrace()
                emit(Resource.Error(
                    message = "unknown Error occurred"
                ))
                if(e is CancellationException) throw  e
            }


            emit(Resource.Loading(isLoading = false))

            return@flow
        }
    }

    override suspend fun getAllSavedTitles(): Flow<List<TitleEntity>> {
        val titleEntityLocals =  titleDao.getAllTitleSortedByDateSavedDesc()

        return  titleEntityLocals.map {
            listTitleEntityLocal ->
            listTitleEntityLocal.map {
                it.toTitleEntity()
            }
        }
    }

    override suspend fun insertTitle(titleEntity: TitleEntity) {
        titleDao.insertTitle(titleEntity.toTitleEntityLocal())
    }

    override suspend fun insertArticles(articleEntityLocals: List<ArticleEntityLocal>) {
        articleDao.insertArticles(articleEntityLocals)
    }

    override suspend fun deleteAllArticles() {
        articleDao.deleteAllArticles()
    }

    override suspend fun getAllArticles(): List<ArticleEntityLocal> {
       return articleDao.getAllArticles()
    }
}