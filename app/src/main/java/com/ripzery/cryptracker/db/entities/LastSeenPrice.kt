package com.ripzery.cryptracker.db.entities

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.arch.persistence.room.TypeConverters
import com.ripzery.cryptracker.db.DateTypeConverter
import java.util.*

/**
 * Created by ripzery on 10/30/17.
 */
@Entity(tableName = "last_seen_price")
@TypeConverters(DateTypeConverter::class)
data class LastSeenPrice(
        @PrimaryKey var id: String = "",
        @ColumnInfo(name = "bx_price") var bxPrice: Double = 0.0,
        @ColumnInfo(name = "cmc_price") var cmcPrice: Double = 0.0,
        @ColumnInfo(name = "date_modified") var dateModified: Date = Date()
) {

}