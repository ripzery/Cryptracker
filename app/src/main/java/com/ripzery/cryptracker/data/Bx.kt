package com.ripzery.cryptracker.data

import com.google.gson.annotations.SerializedName

/**
 * Created by ripzery on 9/4/17.
 */
data class BxPrice(@SerializedName("26") val omg: PairedCurrency,
                   @SerializedName("28") val evx: PairedCurrency,
                   @SerializedName("21") val eth: PairedCurrency,
                   @SerializedName("1") val btc: PairedCurrency)

// Only thai currency here
data class DynamicBxPrice(val currencies: List<PairedCurrency>)

data class PairedCurrency(@SerializedName("pairing_id") val pairingId: Int,
                          @SerializedName("primary_currency") val primaryCurrency: String,
                          @SerializedName("secondary_currency") val secondaryCurrency: String,
                          @SerializedName("last_price") val lastPrice: Double,
                          val change: Double)