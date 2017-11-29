package com.ripzery.cryptracker.utils

import android.content.SharedPreferences

/**
 * Created by ripzery on 10/23/17.
 */
object SharePreferenceHelper {
    lateinit var sharePref: SharedPreferences
    fun readCryptocurrencySetting(): MutableSet<String> {
        return sharePref.getStringSet("setting_cryptocurrency_list", mutableSetOf("omisego", "everex", "ethereum", "bitcoin"))
    }

    fun readCurrencyTop(): String {
        return sharePref.getString("setting_customize_currency_upper_part", CurrencyConstants.USD)
    }

    fun readCurrencyBottom(): String {
        return sharePref.getString("setting_customize_currency_lower_part", CurrencyConstants.THB)
    }

    fun readShowNotification(): Boolean {
        return sharePref.getBoolean("setting_notification", true)
    }
}