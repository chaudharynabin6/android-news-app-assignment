package com.chaudharynabin6.newapp.data.datasources.remote.dto


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class NewsResponseDto(
    @Json(name = "articles")
    val articles: List<ArticleDto>,
    @Json(name = "status")
    val status: String, // ok
    @Json(name = "totalResults")
    val totalResults: Int // 15314
)