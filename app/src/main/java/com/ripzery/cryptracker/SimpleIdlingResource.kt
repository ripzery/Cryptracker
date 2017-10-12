package com.ripzery.cryptracker

import android.support.test.espresso.IdlingResource
import java.util.concurrent.atomic.AtomicBoolean

/**
 * Created by ripzery on 9/17/17.
 */
class SimpleIdlingResource : IdlingResource {

    @Volatile
    var mCallback: IdlingResource.ResourceCallback? = null

    private var mIsIdleNow: AtomicBoolean = AtomicBoolean(true)

    override fun getName(): String {
        return this.javaClass.name
    }

    override fun isIdleNow(): Boolean {
        return mIsIdleNow.get()
    }

    override fun registerIdleTransitionCallback(callback: IdlingResource.ResourceCallback?) {
        mCallback = callback
    }

    fun setIdleState(isIdleNow: Boolean) {
        mIsIdleNow.set(isIdleNow)
        if (isIdleNow) {
            mCallback?.onTransitionToIdle()
        }
    }
}