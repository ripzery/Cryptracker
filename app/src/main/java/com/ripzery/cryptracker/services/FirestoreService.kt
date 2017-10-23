package com.ripzery.cryptracker.services

import android.app.IntentService
import android.content.Context
import android.content.Intent
import android.provider.Settings
import com.ripzery.cryptracker.utils.FirestoreHelper

/**
 * An [IntentService] subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 *
 *
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
class FirestoreService : IntentService("FirestoreService") {

    override fun onHandleIntent(intent: Intent?) {
        if (intent != null) {
            val pricePair = getPriceCurrencyPair(intent)
            handleActionSetLastSeenPrice(pricePair.first, pricePair.second, intent.action)
        }
    }

    private fun getPriceCurrencyPair(intent: Intent): Pair<Double, Double> {
        val pair: Pair<Double, Double> =
                when (intent.action) {
                    ACTION_SET_LAST_SEEN_PRICE_OMG -> {
                        Pair(intent.getDoubleExtra(EXTRA_OMG_BX_PRICE, 0.toDouble()), intent.getDoubleExtra(EXTRA_OMG_CMC_PRICE, 0.toDouble()))
                    }
                    ACTION_SET_LAST_SEEN_PRICE_EVX -> {
                        Pair(intent.getDoubleExtra(EXTRA_EVX_BX_PRICE, 0.toDouble()), intent.getDoubleExtra(EXTRA_EVX_CMC_PRICE, 0.toDouble()))
                    }
                    else -> Pair(0.toDouble(), 0.toDouble())
                }
        return pair
    }

    private fun getCurrencyFromAction(action: String): String {
        return when (action) {
            ACTION_SET_LAST_SEEN_PRICE_EVX -> "evx"
            ACTION_SET_LAST_SEEN_PRICE_OMG -> "omg"
            else -> {
                ""
            }
        }
    }

    private fun handleActionSetLastSeenPrice(bxPrice: Double, cmcPrice: Double, action: String) {
        val deviceId = Settings.Secure.getString(contentResolver, Settings.Secure.ANDROID_ID)
        FirestoreHelper.addLastSeenPrice(deviceId, cmcPrice, bxPrice, getCurrencyFromAction(action))
    }

    companion object {
        private val ACTION_SET_LAST_SEEN_PRICE_OMG = "com.ripzery.cryptracker.services.action.SET_LAST_SEEN_PRICE_OMG"
        private val ACTION_SET_LAST_SEEN_PRICE_EVX = "com.ripzery.cryptracker.services.action.SET_LAST_SEEN_PRICE_EVX"
        private val EXTRA_OMG_BX_PRICE = "com.ripzery.cryptracker.services.extra.OMG_BX"
        private val EXTRA_OMG_CMC_PRICE = "com.ripzery.cryptracker.services.extra.OMG_CMC"
        private val EXTRA_EVX_BX_PRICE = "com.ripzery.cryptracker.services.extra.EVX_BX"
        private val EXTRA_EVX_CMC_PRICE = "com.ripzery.cryptracker.services.extra.EVX_CMC"


        fun startActionSetLastSeenPriceOMG(context: Context, price: Pair<Double, Double>) {
            val intent = Intent(context, FirestoreService::class.java)
            intent.action = ACTION_SET_LAST_SEEN_PRICE_OMG
            intent.putExtra(EXTRA_OMG_CMC_PRICE, price.first)
            intent.putExtra(EXTRA_OMG_BX_PRICE, price.second)
            context.startService(intent)
        }

        fun startActionSetLastSeenPriceEVX(context: Context, price: Pair<Double, Double>) {
            val intent = Intent(context, FirestoreService::class.java)
            intent.action = ACTION_SET_LAST_SEEN_PRICE_EVX
            intent.putExtra(EXTRA_EVX_CMC_PRICE, price.first)
            intent.putExtra(EXTRA_EVX_BX_PRICE, price.second)
            context.startService(intent)
        }

    }
}
