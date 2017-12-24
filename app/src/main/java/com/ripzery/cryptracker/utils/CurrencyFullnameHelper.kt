package com.ripzery.cryptracker.utils


/**
 * OmiseGO
 *
 * Created by Phuchit Sirimongkolsathien on 11/12/2017 AD.
 * Copyright © 2017 OmiseGO. All rights reserved.
 */

object CurrencyFullnameHelper {
    fun shortToFull(shortName: String): String = when (shortName) {
        CurrencyConstants.OMG_SHORT_NAME.toLowerCase(), CurrencyConstants.OMG_SHORT_NAME -> CurrencyConstants.OMG_FULL_NAME
        CurrencyConstants.EVX_SHORT_NAME.toLowerCase(), CurrencyConstants.EVX_SHORT_NAME -> CurrencyConstants.EVX_FULL_NAME
        CurrencyConstants.BTC_SHORT_NAME.toLowerCase(), CurrencyConstants.BTC_SHORT_NAME -> CurrencyConstants.BTC_FULL_NAME
        CurrencyConstants.ETH_SHORT_NAME.toLowerCase(), CurrencyConstants.ETH_SHORT_NAME -> CurrencyConstants.ETH_FULL_NAME
        CurrencyConstants.BCH_SHORT_NAME.toLowerCase(), CurrencyConstants.BCH_SHORT_NAME -> CurrencyConstants.BCH_FULL_NAME
        CurrencyConstants.DAS_SHORT_NAME.toLowerCase(), CurrencyConstants.DAS_SHORT_NAME -> CurrencyConstants.DAS_FULL_NAME
        CurrencyConstants.GNO_SHORT_NAME.toLowerCase(), CurrencyConstants.GNO_SHORT_NAME -> CurrencyConstants.GNO_FULL_NAME
        CurrencyConstants.LTC_SHORT_NAME.toLowerCase(), CurrencyConstants.LTC_SHORT_NAME -> CurrencyConstants.LTC_FULL_NAME
        CurrencyConstants.REP_SHORT_NAME.toLowerCase(), CurrencyConstants.REP_SHORT_NAME -> CurrencyConstants.REP_FULL_NAME
        CurrencyConstants.XRP_SHORT_NAME.toLowerCase(), CurrencyConstants.XRP_SHORT_NAME -> CurrencyConstants.XRP_FULL_NAME
        CurrencyConstants.XZC_SHORT_NAME.toLowerCase(), CurrencyConstants.XZC_SHORT_NAME -> CurrencyConstants.XZC_FULL_NAME
        else -> throw UnsupportedOperationException("Currency doesn't exist!")
    }

    fun fullToShort(fullName: String): String = when (fullName) {
        CurrencyConstants.OMG_FULL_NAME, CurrencyConstants.OMG_FULL_NAME.toUpperCase() -> CurrencyConstants.OMG_SHORT_NAME
        CurrencyConstants.EVX_FULL_NAME, CurrencyConstants.EVX_FULL_NAME.toUpperCase() -> CurrencyConstants.EVX_SHORT_NAME
        CurrencyConstants.BTC_FULL_NAME, CurrencyConstants.BTC_FULL_NAME.toUpperCase() -> CurrencyConstants.BTC_SHORT_NAME
        CurrencyConstants.ETH_FULL_NAME, CurrencyConstants.ETH_FULL_NAME.toUpperCase() -> CurrencyConstants.ETH_SHORT_NAME
        CurrencyConstants.BCH_FULL_NAME, CurrencyConstants.BCH_FULL_NAME.toUpperCase() -> CurrencyConstants.BCH_SHORT_NAME
        CurrencyConstants.DAS_FULL_NAME, CurrencyConstants.DAS_FULL_NAME.toUpperCase() -> CurrencyConstants.DAS_SHORT_NAME
        CurrencyConstants.GNO_FULL_NAME, CurrencyConstants.GNO_FULL_NAME.toUpperCase() -> CurrencyConstants.GNO_SHORT_NAME
        CurrencyConstants.LTC_FULL_NAME, CurrencyConstants.LTC_FULL_NAME.toUpperCase() -> CurrencyConstants.LTC_SHORT_NAME
        CurrencyConstants.REP_FULL_NAME, CurrencyConstants.REP_FULL_NAME.toUpperCase() -> CurrencyConstants.REP_SHORT_NAME
        CurrencyConstants.XRP_FULL_NAME, CurrencyConstants.XRP_FULL_NAME.toUpperCase() -> CurrencyConstants.XRP_SHORT_NAME
        CurrencyConstants.XZC_FULL_NAME, CurrencyConstants.XZC_FULL_NAME.toUpperCase() -> CurrencyConstants.XZC_SHORT_NAME
        else -> throw UnsupportedOperationException("Currency doesn't exist!")
    }
}