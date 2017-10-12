package com.ripzery.cryptracker.pages

import android.os.Bundle
import android.support.animation.SpringAnimation
import android.support.animation.SpringForce
import android.support.test.espresso.idling.CountingIdlingResource
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.TextView
import com.ripzery.cryptracker.R
import com.ripzery.cryptracker.network.DataSource
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_price.*

class PriceActivity : AppCompatActivity() {
    private val mDisposableList: CompositeDisposable = CompositeDisposable()
    private val mHandleAPIError: (Throwable) -> Unit = { error -> Log.d("Error", error.message) }

    lateinit private var mAnimSpringBx: SpringAnimation
    lateinit private var mAnimSpringBxScaleX: SpringAnimation
    lateinit private var mAnimSpringBxScaleY: SpringAnimation
    lateinit private var mAnimSpringCryptowatch: SpringAnimation

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_price)

        mAnimSpringBx = SpringAnimation(tvBx, SpringAnimation.TRANSLATION_Y, 0f)
        mAnimSpringBxScaleX = SpringAnimation(tvBx, SpringAnimation.SCALE_X, 1f)
        mAnimSpringBxScaleY = SpringAnimation(tvBx, SpringAnimation.SCALE_Y, 1f)
        mAnimSpringCryptowatch = SpringAnimation(tvCryptoWatch, SpringAnimation.TRANSLATION_Y, 0f)

        mAnimSpringBx.spring.dampingRatio = SpringForce.DAMPING_RATIO_HIGH_BOUNCY
        mAnimSpringBx.spring.stiffness = SpringForce.STIFFNESS_VERY_LOW

        mAnimSpringCryptowatch.spring.dampingRatio = SpringForce.DAMPING_RATIO_HIGH_BOUNCY
        mAnimSpringCryptowatch.spring.stiffness = SpringForce.STIFFNESS_LOW

        mAnimSpringBxScaleY.spring.stiffness = SpringForce.STIFFNESS_VERY_LOW
        mAnimSpringBxScaleY.spring.dampingRatio = SpringForce.DAMPING_RATIO_HIGH_BOUNCY
        mAnimSpringBxScaleX.spring.stiffness = SpringForce.STIFFNESS_VERY_LOW
        mAnimSpringBxScaleX.spring.dampingRatio = SpringForce.DAMPING_RATIO_HIGH_BOUNCY


    }

    private fun pollingPrice() {
        val d = DataSource.getOMGPriceForInterval(5, mHandleAPIError) { cryptoWatch, bx ->
            tvBx.scaleX = 0.8f
            tvBx.scaleY = 0.8f
            tvCryptoWatch.translationY = -100f

            tvCryptoWatch.text = cryptoWatch
            tvBx.text = bx

//            mAnimSpringBx.start()
            mAnimSpringBxScaleX.start()
            mAnimSpringBxScaleY.start()
            mAnimSpringCryptowatch.start()
        }
        mDisposableList.add(d)
    }

    override fun onStart() {
        super.onStart()
        pollingPrice()
    }

    override fun onStop() {
        super.onStop()
        mDisposableList.clear()
    }

    private fun handleAPISuccess(type: String, price: String, displayTextView: TextView) {
        Log.d(type, price)
        displayTextView.text = price
    }
}
