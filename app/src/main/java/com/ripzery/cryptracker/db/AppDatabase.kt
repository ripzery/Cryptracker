package com.ripzery.cryptracker.db

import android.arch.persistence.db.SupportSQLiteOpenHelper
import android.arch.persistence.room.Database
import android.arch.persistence.room.DatabaseConfiguration
import android.arch.persistence.room.InvalidationTracker
import android.arch.persistence.room.RoomDatabase

/**
 * Created by ripzery on 10/30/17.
 */

@Database(entities = arrayOf(LastSeenPrice::class), version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun lastSeen(): LastSeenPriceDao
}