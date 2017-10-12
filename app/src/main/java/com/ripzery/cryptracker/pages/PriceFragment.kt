package com.ripzery.cryptracker.pages

import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ripzery.cryptracker.R
import com.ripzery.cryptracker.SpringHelper
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.fragment_price.*

/**
 * Created by ripzery on 7/20/16.
 */

class PriceFragment : Fragment() {

    /** Variable zone **/
    lateinit var param1: String
    private val mDisposableList: CompositeDisposable = CompositeDisposable()
    private val mHandleAPIError: (Throwable) -> Unit = { error -> Log.d("Error", error.message) }

    private val mSpringHelper: SpringHelper<View, View> by lazy { SpringHelper(tvBx, tvCryptoWatch) }

    /** Static method zone **/
    companion object {
        val ARG_1 = "ARG_1"

        fun newInstance(param1: String): PriceFragment {
            val bundle: Bundle = Bundle()
            bundle.putString(ARG_1, param1)
            val templateFragment: PriceFragment = PriceFragment()
            templateFragment.arguments = bundle
            return templateFragment
        }

    }

    /** Activity method zone  **/

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            /* if newly created */
            param1 = arguments.getString(ARG_1)
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView: View = inflater!!.inflate(R.layout.fragment_price, container, false)

        return rootView
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initInstance()
    }

    /** Method zone **/

    private fun initInstance() {

    }
}