package com.ripzery.cryptracker

import android.support.animation.SpringAnimation
import android.support.animation.SpringForce
import android.view.View

/**
 * Created by ripzery on 10/12/17.
 */
class SpringHelper<out V1 : View, out V2 : View>(v1: V1, v2: V2) {
    private val mAnimSpringPriceTop: SpringAnimation = SpringAnimation(v1, SpringAnimation.TRANSLATION_Y, 0f).apply {
        spring.dampingRatio = SpringForce.DAMPING_RATIO_HIGH_BOUNCY
        spring.stiffness = SpringForce.STIFFNESS_LOW
    }
    private val mAnimSpringPriceTopScaleX: SpringAnimation = SpringAnimation(v1, SpringAnimation.SCALE_X, 1f).apply {
        spring.stiffness = SpringForce.STIFFNESS_VERY_LOW
        spring.dampingRatio = SpringForce.DAMPING_RATIO_HIGH_BOUNCY
    }
    private val mAnimSpringPriceTopScaleY: SpringAnimation = SpringAnimation(v1, SpringAnimation.SCALE_Y, 1f).apply {
        spring.stiffness = SpringForce.STIFFNESS_VERY_LOW
        spring.dampingRatio = SpringForce.DAMPING_RATIO_HIGH_BOUNCY
    }
    private val mAnimSpringBottom: SpringAnimation = SpringAnimation(v2, SpringAnimation.TRANSLATION_Y, 0f).apply {
        spring.dampingRatio = SpringForce.DAMPING_RATIO_HIGH_BOUNCY
        spring.stiffness = SpringForce.STIFFNESS_LOW
    }

    fun start(){
        mAnimSpringPriceTop.start()
        mAnimSpringBottom.start()
//        mAnimSpringPriceTopScaleX.start()
//        mAnimSpringPriceTopScaleY.start()
    }
}