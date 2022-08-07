package com.chaudharynabin6.newapp.data.datasources.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ArticleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArticles(
        articleEntityLocals: List<ArticleEntityLocal>
    )

    @Query("""delete from article_table""")
    suspend fun deleteAllArticles()

    @Query("""
        select * from article_table
    """)
    suspend fun getAllArticles() : List<ArticleEntityLocal>

}