package com.ripzery.cryptracker.db

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy.REPLACE
import android.arch.persistence.room.Query

/**
 * Created by ripzery on 10/30/17.
 */
@Dao
interface LastSeenPriceDao {
    @Query("SELECT * FROM last_seen_price")
    fun getAll(): List<LastSeenPrice>

    @Query("SELECT * from last_seen_price where id == :arg0")
    fun getPrice(id: Int) : LastSeenPrice

    @Insert(onConflict = REPLACE)
    fun insert(lastSeenPrice: LastSeenPrice)

    @Delete
    fun delete(lastSeenPrice: LastSeenPrice)
}