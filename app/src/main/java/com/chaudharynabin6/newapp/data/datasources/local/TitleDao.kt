package com.chaudharynabin6.newapp.data.datasources.local

import androidx.lifecycle.LiveData
import androidx.room.*


@Dao
abstract  class TitleDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
   abstract suspend fun insertTitle(
         titleLocalEntity: TitleEntityLocal
    )

    @Query("""select * from title_table order by date_saved desc""")
   abstract  fun getAllTitleSortedByDateSavedDesc() : LiveData<TitleEntityLocal>

}