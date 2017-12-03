package com.ripzery.cryptracker

import android.support.test.espresso.Espresso
import android.support.test.espresso.ViewInteraction
import android.support.test.espresso.assertion.ViewAssertions
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.espresso.matcher.ViewMatchers.withText
import org.hamcrest.Matchers.not

/**
 * Created by ripzery on 10/10/17.
 */

/**
 * Alternative to espresso class. Just add some milk, boom Latte!
 */
class LatteView(layoutId: Int) {
    val interaction: ViewInteraction = Espresso.onView(withId(layoutId))
}

class LatteActions(private val interaction: ViewInteraction) {

}

infix fun LatteView.check(lambda: LatteViewAssertions.() -> Unit) {
    lambda(LatteViewAssertions(interaction))
}

class LatteViewAssertions(private val interaction: ViewInteraction) {
    fun hasText(text: String) {
        interaction.check(ViewAssertions.matches(ViewMatchers.withText(text)))
    }

    fun notHasText(text: String) {
        interaction.check(ViewAssertions.matches(not(withText(text))))
    }
}