package com.chaudharynabin6.newapp.data.datasources.remote.dto


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SourceDto(
    @Json(name = "id")
    val id: String?, // the-times-of-india
    @Json(name = "name")
    val name: String // Hibridosyelectricos.com
)