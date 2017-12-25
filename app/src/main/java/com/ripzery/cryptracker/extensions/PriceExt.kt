package com.ripzery.cryptracker.extensions

import com.ripzery.cryptracker.db.entities.LastSeenPrice

/**
 * Created by ripzery on 10/23/17.
 */
private val USD_TO_THB = 32.75
private val THB_TO_USD = 0.03

fun Double.to2Precision(): String {
    return "%.2f".format(this)
}

fun LastSeenPrice.to2Precision(): LastSeenPrice {
    return this.apply {
        bxPrice = bxPrice.to2Precision().toDouble()
        cmcPrice = cmcPrice.to2Precision().toDouble()
    }
}

fun LastSeenPrice.applyCurrency(currency: Pair<String, String>): LastSeenPrice {
    return this.apply {
        when ("${currency.first}${currency.second}") {
            "usdusd" -> bxPrice *= THB_TO_USD
            "usdthb" -> Unit
            "thbthb" -> cmcPrice *= USD_TO_THB
            "thbusd" -> {
                bxPrice *= THB_TO_USD
                cmcPrice *= USD_TO_THB
            }
            "......" -> Unit
            else -> throw UnsupportedOperationException("Unsupported currency!")
        }
    }
}