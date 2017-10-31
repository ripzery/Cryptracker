package com.ripzery.cryptracker.utils

import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on
import org.junit.Assert.assertEquals
import org.junit.platform.runner.JUnitPlatform
import org.junit.runner.RunWith

/**
 * Created by ripzery on 10/31/17.
 */
@RunWith(JUnitPlatform::class)
class CurrencyIdHelperTest : Spek({
    given("CurrencyIdHelper") {
        val converter = CurrencyIdHelper
        on("convert currency of omg to id") {
            val actualId = converter.getId("omg")
            it("id should be 26") {
                assertEquals(26, actualId)
            }
        }
    }
})
