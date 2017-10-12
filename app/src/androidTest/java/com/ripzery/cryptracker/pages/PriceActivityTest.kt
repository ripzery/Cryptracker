package com.ripzery.cryptracker.pages


import android.support.test.espresso.IdlingRegistry
import android.support.test.espresso.IdlingResource
import android.support.test.espresso.ViewInteraction
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.test.suitebuilder.annotation.LargeTest

import com.ripzery.cryptracker.LatteView
import com.ripzery.cryptracker.R

import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.isDisplayed
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.espresso.matcher.ViewMatchers.withText
import android.view.View
import com.ripzery.cryptracker.check
import org.hamcrest.Matchers.allOf
import org.hamcrest.Matchers.not

@LargeTest
@RunWith(AndroidJUnit4::class)
class PriceActivityTest {

    @Rule @JvmField
    var mActivityTestRule = ActivityTestRule(PriceActivity::class.java)

    @Test
    fun priceActivityTest() {
        LatteView(R.id.tvBx) check {
            notHasText("...")
        }
    }
}
