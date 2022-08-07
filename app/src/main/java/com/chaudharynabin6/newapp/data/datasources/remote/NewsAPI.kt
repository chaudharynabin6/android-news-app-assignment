package com.chaudharynabin6.newapp.data.datasources.remote

import com.chaudharynabin6.newapp.BuildConfig
import com.chaudharynabin6.newapp.data.datasources.remote.dto.NewsResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsAPI {

    @GET("v2/everything/?sortBy=popularity")
    suspend fun getNews(
        @Query(value = "apiKey") apiKey : String = BuildConfig.API_KEY,
        @Query(value = "q") q : String
    ) : NewsResponseDto

    companion object {
        const val BASE_URL = "https://newsapi.org/"
    }
}