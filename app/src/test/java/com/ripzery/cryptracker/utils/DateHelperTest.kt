package com.ripzery.cryptracker.utils

import junit.framework.Assert.assertNotNull
import org.junit.Test
import java.util.*

/**
 * OmiseGO
 *
 *
 * Created by Phuchit Sirimongkolsathien on 11/13/2017 AD.
 * Copyright © 2017 OmiseGO. All rights reserved.
 */

class DateHelperTest {
    @Test
    fun getReadableDate() {
        println(DateHelper.getReadableDate(Date()))
        assertNotNull(DateHelper.getReadableDate(Date()).split(" ")[0])
        assertNotNull(DateHelper.getReadableDate(Date()).split(" ")[1])
    }
}