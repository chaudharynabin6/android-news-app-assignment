package com.chaudharynabin6.newapp.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chaudharynabin6.newapp.data.mapper.toArticleEntity
import com.chaudharynabin6.newapp.data.mapper.toArticleEntityLocal
import com.chaudharynabin6.newapp.domain.entity.ArticleEntity
import com.chaudharynabin6.newapp.domain.entity.TitleEntity
import com.chaudharynabin6.newapp.domain.repository.NewsRepository
import com.chaudharynabin6.newapp.other.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import timber.log.Timber
import javax.inject.Inject

//https://proandroiddev.com/no-more-livedata-in-your-repository-there-are-better-options-25a7557b0730
@HiltViewModel
class NewsHeadlineViewModel @Inject constructor(
    private val repository: NewsRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.Default,
) : ViewModel() {

    //     states
    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _errorMessage = MutableSharedFlow<String>()
    val errorMessage = _errorMessage.asSharedFlow()

    private val _articleList = MutableStateFlow<List<ArticleEntity>>(listOf())
    val articleList = _articleList.asStateFlow()

    init {
        sendEvent(event = NewsHeadlineViewModelEvents.LoadCachedNews)
    }

    var job: Job? = null
    private fun searchNews(query: String) {
        viewModelScope.launch(dispatcher) {

            job?.cancel()


            job = viewModelScope.launch(dispatcher) {
                delay(300)
                Timber.e("job")
                repository.getArticles(query = query).collect() { resource ->
                    when (resource) {
                        is Resource.Success -> {
                            resource.data?.let {
                                _articleList.emit(it)
                                sendEvent(
                                    event = NewsHeadlineViewModelEvents.CacheLastNews(it)
                                )
                            }
                        }
                        is Resource.Error -> {
                            _errorMessage.emit(resource.message ?: "failed to get news")
                        }
                        is Resource.Loading -> {
                            _isLoading.emit(resource.isLoading)
                        }
                    }
                }
            }


        }
    }

    private fun saveNews(articleEntity: ArticleEntity) {
        viewModelScope.launch(dispatcher) {
            repository.insertTitle(
                titleEntity = TitleEntity(
                    title = articleEntity.title
                )
            )
        }
    }

    private fun changeSaveIconColor(articleEntity: ArticleEntity) {
        viewModelScope.launch(dispatcher) {

            val newArticles = _articleList.value.map {
                if (it.id == articleEntity.id) {
                    return@map it.copy(
                        isSaved = true
                    )
                }
                it
            }

            _articleList.emit( newArticles)
        }

    }

    fun sendEvent(event: NewsHeadlineViewModelEvents) {
        when (event) {
            is NewsHeadlineViewModelEvents.SearchNews -> {
                searchNews(
                    query = event.query
                )
            }
            is NewsHeadlineViewModelEvents.SaveNews -> {
                saveNews(
                    articleEntity = event.articleEntity
                )
                changeSaveIconColor(
                    articleEntity = event.articleEntity
                )

            }
            is NewsHeadlineViewModelEvents.CacheLastNews -> {
                cacheLastNews(event.articles)
            }
            is NewsHeadlineViewModelEvents.LoadCachedNews -> {
                loadCachedNews()
            }
        }
    }

    private fun cacheLastNews(articles: List<ArticleEntity>) {
        viewModelScope.launch(dispatcher) {
            repository.deleteAllArticles()
            repository.insertArticles(
                articles.map { entity -> entity.toArticleEntityLocal() }
            )

        }
    }

    private fun loadCachedNews() {
        viewModelScope.launch(dispatcher) {
            val listArticleEntityLocal = repository.getAllArticles()
            val articles = listArticleEntityLocal.map { it.toArticleEntity() }
            _articleList.emit(articles)
        }
    }
}

sealed class NewsHeadlineViewModelEvents() {
    data class SearchNews(val query: String) : NewsHeadlineViewModelEvents()
    data class SaveNews(val articleEntity: ArticleEntity) : NewsHeadlineViewModelEvents()
    data class CacheLastNews(val articles: List<ArticleEntity>) : NewsHeadlineViewModelEvents()
    object LoadCachedNews : NewsHeadlineViewModelEvents()
}


