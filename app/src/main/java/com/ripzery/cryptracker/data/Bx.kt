package com.ripzery.cryptracker.data

import com.google.gson.annotations.SerializedName

/**
 * Created by ripzery on 9/4/17.
 */
data class BxOMG(@SerializedName("26") val omg: PairedCurrency)

data class PairedCurrency(@SerializedName("pairing_id") val pairingId: Int, @SerializedName("last_price") val lastPrice: Double, val change: Double)