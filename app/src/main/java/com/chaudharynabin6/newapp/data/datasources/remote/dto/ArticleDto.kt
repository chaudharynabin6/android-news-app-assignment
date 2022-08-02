package com.chaudharynabin6.newapp.data.datasources.remote.dto


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ArticleDto(
    @Json(name = "author")
    val author: String?, // Javier Gómara
    @Json(name = "content")
    val content: String, // Vivimos en un momento de transición. En la industria del automóvil están cambiando muchas cosas muy deprisa. La transición a la movilidad eléctrica ha supuesto un cambio de rumbo, una nueva dirección… [+2900 chars]
    @Json(name = "description")
    val description: String, // En los próximos años General Motors quiere convertirse en una superpotencia eléctrica. Aunque las cifras son todavía algo modestas, las altas reservas de eléctricos demuestran el buen camino emprendido por la compañía.
    @Json(name = "publishedAt")
    val publishedAt: String, // 2022-08-01T11:59:25Z
    @Json(name = "source")
    val source: SourceDto,
    @Json(name = "title")
    val title: String, // Aumenta el número de reservas de coches eléctricos en General Motors: comienza el cambio
    @Json(name = "url")
    val url: String, // https://www.hibridosyelectricos.com/articulo/actualidad/aumentan-reservas-coches-electricos-general-motors/20220801080839060936.html
    @Json(name = "urlToImage")
    val urlToImage: String? // https://www.hibridosyelectricos.com/media/hibridos/images/2022/04/14/2022041415483931695.jpg
)