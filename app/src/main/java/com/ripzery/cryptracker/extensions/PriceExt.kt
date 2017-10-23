package com.ripzery.cryptracker.extensions

/**
 * Created by ripzery on 10/23/17.
 */
fun Double.to2Precision(): String {
    return "%.2f".format(this)
}