package com.chaudharynabin6.newapp.data.datasources.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "article_table")
data class ArticleEntityLocal(
    @PrimaryKey(autoGenerate = true)
    val id : Int? = null,
    var author : String,
    val content : String,
    val description : String,
    val publishedDate : Date,
    val title : String,
    val url : String,
    val urlToImage : String,
    val source : String,
    val isSaved : Boolean
)
