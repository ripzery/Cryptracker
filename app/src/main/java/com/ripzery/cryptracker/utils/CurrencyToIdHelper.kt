package com.ripzery.cryptracker.utils

/**
 * Created by ripzery on 10/30/17.
 */
object CurrencyToIdHelper {
    fun getId(currency: String): Int {
        return when (currency) {
            "omg" -> 26
            "evx" -> 28
            "eth" -> 21
            "btc" -> 1
            else -> throw UnsupportedOperationException("Unknown currency $currency")
        }
    }
}