package com.chaudharynabin6.newapp.domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.*
@Parcelize
data class ArticleEntity(
    val id : Int,
    val author : String,
    val content : String,
    val description : String,
    val publishedAt : Date,
    val title : String,
    val url : String,
    val urlToImage: String,
    val source : String,
    val isSaved : Boolean = false
) : Parcelable