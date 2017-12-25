package com.ripzery.cryptracker.pages.price.cryptocurrency

import android.annotation.SuppressLint
import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ripzery.cryptracker.R
import com.ripzery.cryptracker.db.entities.LastSeenPrice
import com.ripzery.cryptracker.extensions.applyCurrency
import com.ripzery.cryptracker.extensions.getViewModel
import com.ripzery.cryptracker.extensions.to2Precision
import com.ripzery.cryptracker.utils.CurrencyFullnameHelper
import com.ripzery.cryptracker.utils.SharePreferenceHelper
import com.ripzery.cryptracker.utils.SpringHelper
import kotlinx.android.synthetic.main.fragment_price.*

/**
 * Created by ripzery on 7/20/16.
 */

class CryptocurrencyFragment : Fragment() {

    /** Variable zone **/
    private lateinit var mCryptocurrency: String
    private lateinit var mSpringHelper: SpringHelper<View, View>

    private val mViewModel by lazy { getViewModel(CryptocurrencyViewModel::class.java).apply { init(mCryptocurrency) } }


    /** Static method zone **/
    companion object {
        val ARG_1 = "ARG_1"
        fun newInstance(cryptocurrency: String): CryptocurrencyFragment {
            return CryptocurrencyFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_1, cryptocurrency)
                }
            }
        }

    }

    /** Activity method zone  **/

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mCryptocurrency = arguments?.getString(ARG_1) ?: "omg"
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_price, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initInstance()
    }

    override fun onStart() {
        super.onStart()
        mSpringHelper = SpringHelper(tvBx, tvCoinMarketCap)
        Log.d("currency", CurrencyFullnameHelper.fullToShort(mCryptocurrency))

        mViewModel.pollingPrice(CurrencyFullnameHelper.fullToShort(mCryptocurrency)).observe(this, mObservePriceChanged)
    }

    /** Method zone **/

    @SuppressLint("SetTextI18n")
    private fun initInstance() {
        tvSourceTop.text = "$mCryptocurrency${tvSourceTop.text}"
        tvSourceBottom.text = "$mCryptocurrency${tvSourceBottom.text}"
    }

    private var mObservePriceChanged: Observer<LastSeenPrice> = Observer {
        val price = it?.applyCurrency(SharePreferenceHelper.readCurrencyTop() to SharePreferenceHelper.readCurrencyBottom())
        tvBx.scaleX = 0.8f
        tvBx.scaleY = 0.8f
        tvCoinMarketCap.translationY = -100f
        tvCoinMarketCap.text = price?.cmcPrice?.to2Precision() ?: "..."
        tvBx.text = it?.bxPrice?.to2Precision() ?: "..."
        mSpringHelper.start()
    }
}