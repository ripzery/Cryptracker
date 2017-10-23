package com.ripzery.cryptracker.utils

import android.os.Build

/**
 * Created by ripzery on 10/23/17.
 */
object DeviceInfoHelper {
    fun getDeviceName(): String {
        val manufacturer = Build.MANUFACTURER
        val model = Build.MODEL
        if (model.startsWith(manufacturer)) {
            return capitalize(model)
        }
        return capitalize(manufacturer) + " " + model
    }

    private fun capitalize(str: String): String {
        if (str.isEmpty()) return str
        return str.split(" ").joinToString(" ") { it -> it.capitalize() }
    }
}