package com.ripzery.cryptracker.extensions

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProviders
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity

/**
 * Created by ripzery on 10/29/17.
 */
fun <T : ViewModel> FragmentActivity.getViewModel(clazz: Class<T>): T {
    return ViewModelProviders.of(this).get(clazz)
}

fun <T : ViewModel> Fragment.getViewModel(clazz: Class<T>): T {
    return ViewModelProviders.of(this).get(clazz)
}