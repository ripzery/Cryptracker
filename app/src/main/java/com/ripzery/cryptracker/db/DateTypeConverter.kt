package com.ripzery.cryptracker.db

import android.arch.persistence.room.TypeConverter
import java.util.*

/**
 * Created by ripzery on 10/30/17.
 */
object DateTypeConverter {
    @TypeConverter
    @JvmStatic fun toDate(time: Long): Date {
        return Date(time)
    }

    @TypeConverter
    @JvmStatic fun toLong(date: Date): Long {
        return date.time
    }
}