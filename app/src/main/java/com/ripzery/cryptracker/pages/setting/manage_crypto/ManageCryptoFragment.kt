package com.ripzery.cryptracker.pages.setting.manage_crypto

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ripzery.cryptracker.R
import kotlinx.android.synthetic.main.fragment_manage_cryptocurrency.*

/**
 * Created by ripzery on 7/20/16.
 */

class ManageCryptoFragment : Fragment() {

    /** Variable zone **/
    private val tabs = listOf("Active", "Inactive")


    /** Static method zone **/
    companion object {

        fun newInstance(): ManageCryptoFragment {
            val manageCryptoFragment = ManageCryptoFragment()
            return manageCryptoFragment
        }

    }

    /** Activity method zone  **/

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            /* if newly created */
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView: View = inflater!!.inflate(R.layout.fragment_manage_cryptocurrency, container, false)

        return rootView
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initInstance()
    }

    /** Method zone **/

    private fun initInstance() {
        val adapter = ManageCryptoAdapter(tabs, childFragmentManager)
        viewPager.adapter = adapter
        tabLayout.setupWithViewPager(viewPager)
    }

    /* Inner class zone */
    class ManageCryptoAdapter(val tabList: List<String>, fm: FragmentManager) : FragmentPagerAdapter(fm) {
        override fun getItem(position: Int): Fragment = if (position == 0) CryptoListFragment.newInstance(true) else CryptoListFragment.newInstance(false)
        override fun getCount() = tabList.size
        override fun getPageTitle(position: Int) = tabList[position]
    }
}