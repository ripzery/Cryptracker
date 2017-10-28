package com.ripzery.cryptracker.pages.price

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ripzery.cryptracker.R
import com.ripzery.cryptracker.SpringHelper
import com.ripzery.cryptracker.extensions.to2Precision
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
    private lateinit var mCurrencyTop: String
    private lateinit var mCurrencyBottom: String
    private lateinit var mSpringHelper: SpringHelper<View, View>
    private val USD_TO_THB = 33.23
    private val THB_TO_USD = 0.03

    /** Static method zone **/
    companion object {
        val ARG_1 = "ARG_1"
        val ARG_2 = "ARG_2"
        val ARG_3 = "ARG_3"
        fun newInstance(cryptocurrency: String, currencyTop: String, currencyBottom: String): PriceFragment {
            return PriceFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_1, cryptocurrency)
                    putString(ARG_2, currencyTop)
                    putString(ARG_3, currencyBottom)
                }
            }
        }

    }

    /** Activity method zone  **/

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mCryptocurrency = arguments.getString(ARG_1)
        mCurrencyTop = arguments.getString(ARG_2)
        mCurrencyBottom = arguments.getString(ARG_3)
//            mCryptocurrency = savedInstanceState.getString("cryptocurrency")
//            mCurrencyTop = savedInstanceState.getString("currencyTop")
//            mCurrencyBottom = savedInstanceState.getString("currencyBottom")

    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView: View = inflater!!.inflate(R.layout.fragment_price, container, false)

        return rootView
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initInstance()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putString("cryptocurrency", mCryptocurrency)
        outState.putString("currencyTop", mCurrencyTop)
        outState.putString("currencyBottom", mCurrencyBottom)
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

            if (mCurrencyTop == "usd") {
                tvCoinMarketCap.text = coinMarketCap
            } else if (mCurrencyTop == "thb") {
                tvCoinMarketCap.text = (coinMarketCap.toFloat() * USD_TO_THB).to2Precision()
            }

            if (mCurrencyBottom == "usd") {
                tvBx.text = (bx.toFloat() * THB_TO_USD).to2Precision()
            } else if (mCurrencyBottom == "thb") {
                tvBx.text = bx
            }

            mSpringHelper.start()
        }
        mDisposableList.add(d)
    }
}