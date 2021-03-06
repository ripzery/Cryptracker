package com.ripzery.cryptracker.db

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy.REPLACE
import android.arch.persistence.room.Query
import com.ripzery.cryptracker.db.entities.LastSeenPrice
import io.reactivex.Flowable

/**
 * Created by ripzery on 10/30/17.
 */
@Dao
interface LastSeenPriceDao {
    @Query("SELECT * FROM last_seen_price ORDER BY id ASC")
    fun getAll(): List<LastSeenPrice>

    @Query("SELECT * from last_seen_price where id == :arg0")
    fun getPrice(id: String): LastSeenPrice?

    @Insert(onConflict = REPLACE)
    fun insert(lastSeenPrice: LastSeenPrice)

    @Query("UPDATE last_seen_price SET bx_price = :arg1 WHERE id = :arg0")
    fun update(id: String, price: Double)

    @Query("SELECT * from last_seen_price where id == :arg0")
    fun getPriceSync(id: String): LiveData<LastSeenPrice>

    @Delete
    fun delete(lastSeenPrice: LastSeenPrice)
}