package com.ripzery.cryptracker.pages.price

import android.support.test.filters.LargeTest
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.ripzery.cryptracker.LatteView
import com.ripzery.cryptracker.R
import com.ripzery.cryptracker.check
import com.ripzery.cryptracker.make
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
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
        LatteView(R.id.viewPager) make {
            goRight()
            goRight()
            goRight()
        }
    }
}
