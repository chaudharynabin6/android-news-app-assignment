package com.chaudharynabin6.newapp.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chaudharynabin6.newapp.domain.entity.TitleEntity
import com.chaudharynabin6.newapp.domain.repository.NewsRepository
import com.chaudharynabin6.newapp.other.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SavedNewsViewModel @Inject constructor(
    private val repository: NewsRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.Default
) : ViewModel() {

    init {
        sendEvent(
            event = SavedTitleViewModelEvents.GetAllTitles
        )
    }

//    states
    private val _savedTitles = MutableStateFlow<List<TitleEntity>>(listOf())
    val savedTitles = _savedTitles.asStateFlow()


    //    eventHandling
    fun sendEvent(event : SavedTitleViewModelEvents){
        when(event){
            is SavedTitleViewModelEvents.GetAllTitles -> {
                getAllTitlesOrderByDateDesc()
            }
        }
    }

    private fun getAllTitlesOrderByDateDesc(){
        viewModelScope.launch(dispatcher) {
            repository.getAllSavedTitles().collect(){
             _savedTitles.emit(it)
            }
        }
    }


    sealed class SavedTitleViewModelEvents(){
        object GetAllTitles : SavedTitleViewModelEvents()
    }
}