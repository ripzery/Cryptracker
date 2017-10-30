package com.ripzery.cryptracker.extensions

import com.ripzery.cryptracker.db.entities.LastSeenPrice

/**
 * Created by ripzery on 10/23/17.
 */
fun Double.to2Precision(): String {
    return "%.2f".format(this)
}

fun LastSeenPrice.to2Precision(): LastSeenPrice {
    return this.apply {
        bxPrice = bxPrice.to2Precision().toDouble()
        cmcPrice = cmcPrice.to2Precision().toDouble()
    }
}