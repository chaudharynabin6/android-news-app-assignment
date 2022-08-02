package com.chaudharynabin6.newapp.data.mapper

import com.chaudharynabin6.newapp.data.datasources.remote.dto.ArticleDto
import com.chaudharynabin6.newapp.domain.entity.ArticleEntity
import java.time.Instant
import java.util.*

fun ArticleDto.toArticleEntity(): ArticleEntity? {
//    https://stackoverflow.com/questions/4525850/java-convert-iso-8601-2010-12-16t133350-513852z-to-date-object
    val instant = Instant.parse(publishedAt)
    val date = Date.from(instant)
    val author = author ?: return null
    val urlToImage = urlToImage ?: return null
    return ArticleEntity(
        author = author,
        content = content,
        description = description,
        title = title,
        url = url,
        urlToMage = urlToImage,
        source = source.name,
        publishedAt = date
    )
}