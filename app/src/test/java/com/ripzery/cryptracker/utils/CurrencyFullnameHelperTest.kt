package com.ripzery.cryptracker.utils

import org.amshove.kluent.shouldEqual
import org.junit.Before
import org.junit.Test

/**
 * OmiseGO
 *
 *
 * Created by Phuchit Sirimongkolsathien on 3/12/2017 AD.
 * Copyright © 2017 OmiseGO. All rights reserved.
 */

class CurrencyFullnameHelperTest {

    private lateinit var mCurrencyFullnameHelper: CurrencyFullnameHelper

    @Before
    fun setUp() {
        mCurrencyFullnameHelper = CurrencyFullnameHelper
    }

    @Test
    fun `If currency is found, convert short name of currency to full name should be success`() {
        mCurrencyFullnameHelper.shortToFull("omg") shouldEqual CurrencyConstants.OMG_FULL_NAME
        mCurrencyFullnameHelper.shortToFull("evx") shouldEqual CurrencyConstants.EVX_FULL_NAME
        mCurrencyFullnameHelper.shortToFull("btc") shouldEqual CurrencyConstants.BTC_FULL_NAME
        mCurrencyFullnameHelper.shortToFull("eth") shouldEqual CurrencyConstants.ETH_FULL_NAME
    }

    @Test(expected = UnsupportedOperationException::class)
    fun `If currency is not found, convert short name of currency to full name should be failed`() {
        mCurrencyFullnameHelper.shortToFull("euro")
    }
}