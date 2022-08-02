package com.chaudharynabin6.newapp.data.datasources.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    entities = [TitleEntityLocal::class],
    version = 1
)
@TypeConverters(DateTypeConverter::class)
abstract class NewsDataBase :RoomDatabase() {
        abstract fun dao() : TitleDao
}