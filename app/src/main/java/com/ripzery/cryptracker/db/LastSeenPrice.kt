package com.ripzery.cryptracker.db

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

/**
 * Created by ripzery on 10/30/17.
 */
@Entity(tableName = "last_seen_price")
class LastSeenPrice(
        @PrimaryKey var id: Int,
        @ColumnInfo(name = "bx_price") var bxPrice: Double = 0.0,
        @ColumnInfo(name = "cmc_price") var cmcPrice: Double = 0.0
) {

}