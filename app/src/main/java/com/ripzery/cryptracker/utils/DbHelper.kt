package com.ripzery.cryptracker.utils

import android.arch.persistence.room.Room
import com.ripzery.cryptracker.db.AppDatabase

/**
 * Created by ripzery on 10/30/17.
 */
object DbHelper {
    val db: AppDatabase by lazy {
        Room.databaseBuilder(Contextor.context, AppDatabase::class.java, "cryptracker-db")
                .addMigrations(AppDatabase.MIGRATION_1_2)
                .build()
    }
}