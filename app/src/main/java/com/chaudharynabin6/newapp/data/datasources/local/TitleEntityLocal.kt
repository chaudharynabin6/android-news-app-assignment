package com.chaudharynabin6.newapp.data.datasources.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.Instant
import java.util.*

@Entity(tableName = "title_table")
data  class TitleEntityLocal(
    @PrimaryKey val title : String,
    @ColumnInfo(name = "date_saved")
    val dateSaved : Date = Date.from(Instant.now())
)