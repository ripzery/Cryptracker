package com.ripzery.cryptracker.utils

import java.text.SimpleDateFormat
import java.util.*


/**
 * OmiseGO
 *
 * Created by Phuchit Sirimongkolsathien on 11/13/2017 AD.
 * Copyright © 2017 OmiseGO. All rights reserved.
 */

object DateHelper {

    val DEFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss"
    val dateFormat by lazy { SimpleDateFormat(DEFAULT_DATE_FORMAT, Locale.US) }

    fun getReadableDate(date: Date) = dateFormat.format(date)
}