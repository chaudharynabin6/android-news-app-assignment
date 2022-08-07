package com.chaudharynabin6.newapp.data.mapper

import com.chaudharynabin6.newapp.data.datasources.local.ArticleEntityLocal
import com.chaudharynabin6.newapp.data.datasources.remote.dto.ArticleDto
import com.chaudharynabin6.newapp.domain.entity.ArticleEntity
import java.time.Instant
import java.util.*

fun ArticleDto.toArticleEntity(): ArticleEntity? {
//    https://stackoverflow.com/questions/4525850/java-convert-iso-8601-2010-12-16t133350-513852z-to-date-object
    val instant = Instant.parse(publishedAt)
    val date = Date.from(instant)
    val urlToImage = urlToImage ?: return null
    return ArticleEntity(
        id = this.hashCode(),
        author = author ?: "",
        content = content ?: "",
        description = description ?: "",
        title = title ?: "",
        url = url ?: "",
        urlToImage = urlToImage,
        source = source?.name ?: "",
        publishedAt = date ?: return null
    )
}

fun ArticleEntity.toArticleEntityLocal(): ArticleEntityLocal {
    return ArticleEntityLocal(
        id = id,
        author = author,
        content = content,
        description = description,
        title = title,
        url = url,
        urlToImage = urlToImage,
        source = source,
        publishedDate = publishedAt,
        isSaved = isSaved
    )
}

fun ArticleEntityLocal.toArticleEntity(): ArticleEntity {
    return ArticleEntity(
        id = id!!,
        author = author,
        content = content,
        description = description,
        title = title,
        url = url,
        urlToImage = urlToImage,
        source = source,
        publishedAt = publishedDate,
        isSaved = isSaved
    )
}