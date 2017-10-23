package com.ripzery.cryptracker.pages

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ripzery.cryptracker.R
import com.ripzery.cryptracker.SpringHelper
import com.ripzery.cryptracker.extensions.TAG
import com.ripzery.cryptracker.network.DataSource
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.fragment_price.*

/**
 * Created by ripzery on 7/20/16.
 */

class PriceFragment : Fragment() {

    /** Variable zone **/
    private lateinit var mCryptocurrency: String
    private val mDisposableList: CompositeDisposable = CompositeDisposable()
    private val mHandleAPIError: (Throwable) -> Unit = { error -> Log.d("Error", error.message) }

    private lateinit var mSpringHelper: SpringHelper<View, View>

    /** Static method zone **/
    companion object {
        val ARG_1 = "ARG_1"

        fun newInstance(cryptocurrency: String): PriceFragment {
            return PriceFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_1, cryptocurrency)
                }
            }
        }

    }

    /** Activity method zone  **/

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            /* if newly created */
            mCryptocurrency = arguments.getString(ARG_1)
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView: View = inflater!!.inflate(R.layout.fragment_price, container, false)

        return rootView
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /* Restore state */
        savedInstanceState?.let { mCryptocurrency = savedInstanceState.getString("cryptocurrency") }
        initInstance()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putString("cryptocurrency", mCryptocurrency)
        super.onSaveInstanceState(outState)
    }

    override fun onStart() {
        super.onStart()
        mSpringHelper = SpringHelper(tvBx, tvCoinMarketCap)
        pollingPrice()
    }

    override fun onStop() {
        super.onStop()
        mDisposableList.clear()
    }

    /** Method zone **/

    @SuppressLint("SetTextI18n")
    private fun initInstance() {
        tvSourceTop.text = "$mCryptocurrency${tvSourceTop.text}"
        tvSourceBottom.text = "$mCryptocurrency${tvSourceBottom.text}"
    }

    private fun pollingPrice() {
        val d = DataSource.getPriceForInterval(mCryptocurrency, 5, mHandleAPIError) { coinMarketCap, bx ->
            tvBx.scaleX = 0.8f
            tvBx.scaleY = 0.8f
            tvCoinMarketCap.translationY = -100f

            tvCoinMarketCap.text = coinMarketCap
            tvBx.text = bx

            mSpringHelper.start()
        }
        mDisposableList.add(d)
    }
}