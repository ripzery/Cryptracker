package com.ripzery.cryptracker.pages.setting.manage_crypto

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ripzery.cryptracker.R

/**
 * Created by ripzery on 7/20/16.
 */

class CryptoListFragment : Fragment() {

    /** Variable zone **/
    private var mIsActivate: Boolean = false


    /** Static method zone **/
    companion object {
        val IS_ACTIVATE = "ARG_1"

        fun newInstance(isActivate: Boolean): CryptoListFragment {
            var bundle = Bundle()
            bundle.putBoolean(IS_ACTIVATE, isActivate)
            val templateFragment = CryptoListFragment()
            templateFragment.arguments = bundle
            return templateFragment
        }

    }

    /** Activity method zone  **/

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            /* if newly created */
            mIsActivate = arguments.getBoolean(IS_ACTIVATE)
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView: View = inflater!!.inflate(R.layout.fragment_crypto_list, container, false)

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