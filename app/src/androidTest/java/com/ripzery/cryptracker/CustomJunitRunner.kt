package com.ripzery.cryptracker

import android.support.test.runner.AndroidJUnitRunner
import com.squareup.rx2.idler.Rx2Idler
import io.reactivex.plugins.RxJavaPlugins

/**
 * Created by ripzery on 10/10/17.
 */
class CustomJunitRunner : AndroidJUnitRunner() {
    override fun onStart() {
        RxJavaPlugins.setInitComputationSchedulerHandler(Rx2Idler.create("RxJava 2.x Computation Scheduler"))
        super.onStart()
    }
}