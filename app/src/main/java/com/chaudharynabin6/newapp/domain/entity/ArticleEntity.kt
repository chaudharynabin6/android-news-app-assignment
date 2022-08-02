package com.chaudharynabin6.newapp.domain.entity

import java.util.*

data class ArticleEntity(
    val author : String,
    val content : String,
    val description : String,
    val publishedAt : Date,
    val title : String,
    val url : String,
    val urlToMage: String,
    val source : String
)