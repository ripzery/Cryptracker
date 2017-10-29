package com.ripzery.cryptracker.pages.price.cryptocurrency

import android.annotation.SuppressLint
import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ripzery.cryptracker.R
import com.ripzery.cryptracker.SpringHelper
import com.ripzery.cryptracker.extensions.getViewModel
import kotlinx.android.synthetic.main.fragment_price.*

/**
 * Created by ripzery on 7/20/16.
 */

class CryptocurrencyFragment : Fragment() {

    /** Variable zone **/
    private lateinit var mCryptocurrency: String
    private lateinit var mCurrencyTop: String
    private lateinit var mCurrencyBottom: String
    private lateinit var mSpringHelper: SpringHelper<View, View>

    private val mViewModel by lazy { getViewModel(CryptocurrencyViewModel::class.java).apply { init(mCryptocurrency, mCurrencyTop, mCurrencyBottom) } }


    /** Static method zone **/
    companion object {
        val ARG_1 = "ARG_1"
        val ARG_2 = "ARG_2"
        val ARG_3 = "ARG_3"
        fun newInstance(cryptocurrency: String, currencyTop: String, currencyBottom: String): CryptocurrencyFragment {
            return CryptocurrencyFragment().apply {
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
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_price, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initInstance()
    }

    override fun onStart() {
        super.onStart()
        mSpringHelper = SpringHelper(tvBx, tvCoinMarketCap)
        mViewModel.pollingPrice(mCryptocurrency).observe(this, mObservePriceChanged)
    }

    /** Method zone **/

    @SuppressLint("SetTextI18n")
    private fun initInstance() {
        tvSourceTop.text = "$mCryptocurrency${tvSourceTop.text}"
        tvSourceBottom.text = "$mCryptocurrency${tvSourceBottom.text}"
    }

    private var mObservePriceChanged: Observer<Pair<String, String>> = Observer {
        val bx = it!!.second
        val coinMarketCap = it.first
        tvBx.scaleX = 0.8f
        tvBx.scaleY = 0.8f
        tvCoinMarketCap.translationY = -100f
        tvCoinMarketCap.text = coinMarketCap
        tvBx.text = bx
        mSpringHelper.start()
    }
}