package com.chaudharynabin6.newapp.data.datasources.local

import androidx.room.TypeConverter
import java.time.Instant
import java.util.*

class DateTypeConverter {

    @TypeConverter
    fun toDate(timeInMillis: Long): Date {
        val instant = Instant.ofEpochMilli(timeInMillis)
        return Date.from(instant)
    }

    @TypeConverter
    fun toMillis(date: Date): Long {
        return date.toInstant().toEpochMilli()
    }
}