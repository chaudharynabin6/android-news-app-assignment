package com.chaudharynabin6.newapp.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chaudharynabin6.newapp.domain.entity.ArticleEntity
import com.chaudharynabin6.newapp.domain.entity.TitleEntity
import com.chaudharynabin6.newapp.domain.repository.NewsRepository
import com.chaudharynabin6.newapp.other.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
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
        sendEvent(NewsHeadlineViewModelEvents.SearchNews("tesla"))
    }

    private fun searchNews(query: String) {
        viewModelScope.launch(dispatcher) {
            repository.getArticles(query = query).collect() { resource ->
                when (resource) {
                    is Resource.Success -> {
                        resource.data?.let {
                            _articleList.emit(it)
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

    fun sendEvent(events: NewsHeadlineViewModelEvents) {
        when (events) {
            is NewsHeadlineViewModelEvents.SearchNews -> {
                searchNews(
                    query = events.query
                )
            }
            is NewsHeadlineViewModelEvents.SaveNews -> {
                saveNews(
                    articleEntity = events.articleEntity
                )
                changeSaveIconColor(
                    articleEntity = events.articleEntity
                )

            }
        }
    }


}

sealed class NewsHeadlineViewModelEvents() {
    data class SearchNews(val query: String) : NewsHeadlineViewModelEvents()
    data class SaveNews(val articleEntity: ArticleEntity) : NewsHeadlineViewModelEvents()
}


