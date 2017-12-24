package com.ripzery.cryptracker.db

import android.arch.persistence.db.SupportSQLiteDatabase
import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.migration.Migration
import com.ripzery.cryptracker.db.entities.LastSeenPrice
import java.util.*

/**
 * Created by ripzery on 10/30/17.
 */

@Database(entities = arrayOf(LastSeenPrice::class), version = 3)
abstract class AppDatabase : RoomDatabase() {
    abstract fun lastSeen(): LastSeenPriceDao

    companion object {
        var MIGRATION_1_2: Migration = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE last_seen_price ADD COLUMN date_modified INTEGER NOT NULL DEFAULT " + Date().time)
            }
        }
    }
}