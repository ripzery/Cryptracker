package com.ripzery.cryptracker.pages


import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.test.suitebuilder.annotation.LargeTest
import com.ripzery.cryptracker.LatteView
import com.ripzery.cryptracker.R
import com.ripzery.cryptracker.check
import com.ripzery.cryptracker.pages.price.PriceActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class PriceActivityTest {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(PriceActivity::class.java)

    @Test
    fun priceActivityTest() {
        LatteView(R.id.tvBx) check {
            notHasText("...")
        }
    }

    @Test
    fun `price activity should has 4 tabs`() {

    }
}
