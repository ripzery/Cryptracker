package com.ripzery.cryptracker.utils


/**
 * OmiseGO
 *
 * Created by Phuchit Sirimongkolsathien on 11/12/2017 AD.
 * Copyright © 2017 OmiseGO. All rights reserved.
 */

object CurrencyFullnameHelper {
    fun shortToFull(shortName: String): String = when (shortName) {
        "omg" -> CurrencyConstants.OMG_FULL_NAME
        "evx" -> CurrencyConstants.EVX_FULL_NAME
        "btc" -> CurrencyConstants.BTC_FULL_NAME
        "eth" -> CurrencyConstants.ETH_FULL_NAME
        else -> throw UnsupportedOperationException("Currency doesn't exist!")
    }
}