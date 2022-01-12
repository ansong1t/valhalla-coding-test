package com.valhalla.data.converters

import androidx.room.TypeConverter
import org.threeten.bp.Instant

class UserDBConverters {

    @TypeConverter
    fun fromInstant(value: Instant?): Long? {
        return value?.toEpochMilli()
    }

    @TypeConverter
    fun toInstant(value: Long?): Instant? {
        return if (value != null) {
            Instant.ofEpochMilli(value)
        } else {
            null
        }
    }
}
