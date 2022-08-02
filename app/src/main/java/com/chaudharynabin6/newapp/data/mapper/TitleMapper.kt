package com.chaudharynabin6.newapp.data.mapper

import com.chaudharynabin6.newapp.data.datasources.local.TitleEntityLocal
import com.chaudharynabin6.newapp.domain.entity.TitleEntity

fun TitleEntityLocal.toTitleEntity(): TitleEntity {
    return TitleEntity(
        title = title
    )
}

fun TitleEntity.toTitleEntityLocal() : TitleEntityLocal{
    return TitleEntityLocal(
        title = title
    )
}