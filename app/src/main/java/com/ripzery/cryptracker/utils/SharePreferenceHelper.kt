package com.ripzery.cryptracker.utils

import android.content.SharedPreferences

/**
 * Created by ripzery on 10/23/17.
 */
object SharePreferenceHelper {
    lateinit var sharePref: SharedPreferences
    fun writeDouble(key: String, price: Double) {
        sharePref.edit().apply { putFloat(key, price.toFloat()) }.apply()
    }

    fun readDouble(key: String): Double {
        return sharePref.getFloat(key, 0f).toDouble()
    }

    fun readCryptocurrencySetting(): MutableSet<String> {
        return sharePref.getStringSet("setting_cryptocurrency_list", mutableSetOf("omisego", "everex", "ethereum", "bitcoin"))
    }

    fun readCurrencyTop(): String {
        return sharePref.getString("setting_customize_currency_upper_part", "usd")
    }

    fun readCurrencyBottom(): String {
        return sharePref.getString("setting_customize_currency_lower_part", "thb")
    }
}