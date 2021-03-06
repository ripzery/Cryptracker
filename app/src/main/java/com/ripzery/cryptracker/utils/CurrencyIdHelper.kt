package com.ripzery.cryptracker.utils

/**
 * Created by ripzery on 10/30/17.
 */
object CurrencyIdHelper {
    fun getId(currency: String): Int {
        return when (currency) {
            "omg" -> 26
            "evx" -> 28
            "eth" -> 21
            "btc" -> 1
            else -> throw UnsupportedOperationException("Unknown currency $currency")
        }
    }

    fun getCurrency(id: Int): String {
        return when (id) {
            26 -> "omg"
            28 -> "evx"
            21 -> "eth"
            1 -> "btc"
            else -> throw UnsupportedOperationException("Unknown currency id $id")
        }
    }
}