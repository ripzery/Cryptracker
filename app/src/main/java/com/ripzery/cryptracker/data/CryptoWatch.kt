package com.ripzery.cryptracker.data

import com.google.gson.annotations.SerializedName

/**
 * Created by ripzery on 9/4/17.
 */
data class CoinMarketCapResult(@SerializedName("price_usd") val price: String, @SerializedName("percent_change_24h") val percent24h: String, val symbol: String, val id: String)