package com.ripzery.cryptracker.pages

import android.os.Bundle
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v7.app.AppCompatActivity
import com.ripzery.cryptracker.R
import com.ripzery.cryptracker.network.DataSource
import com.ripzery.cryptracker.services.FirestoreService
import com.ripzery.cryptracker.utils.CurrencyContants
import com.ripzery.cryptracker.utils.SharePreferenceHelper
import kotlinx.android.synthetic.main.activity_price.*

class PriceActivity : AppCompatActivity() {

    private val mCryptocurrencyList: List<String> = listOf("omisego", "everex", "ethereum", "bitcoin")
    private val mPagerAdapter: PricePagerAdapter by lazy { PricePagerAdapter(mCryptocurrencyList, supportFragmentManager) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_price)

        initInstance()
    }

    private fun initInstance() {
        viewPager.adapter = mPagerAdapter
        tabLayout.setupWithViewPager(viewPager)
    }

    override fun onStop() {
        super.onStop()
        if (DataSource.lastPriceOmiseGo != null) {
            FirestoreService.startActionSetLastSeenPriceOMG(this, DataSource.lastPriceOmiseGo!!)
            FirestoreService.startActionSetLastSeenPriceEVX(this, DataSource.lastPriceEvx!!)
            SharePreferenceHelper.writeDouble(CurrencyContants.OMG, DataSource.lastPriceOmiseGo?.second)
            SharePreferenceHelper.writeDouble(CurrencyContants.EVX, DataSource.lastPriceEvx?.second)
        }
    }

    class PricePagerAdapter(private val cryptocurrencyList: List<String>, fm: FragmentManager) : FragmentPagerAdapter(fm) {
        override fun getItem(position: Int) = PriceFragment.newInstance(cryptocurrencyList[position])
        override fun getCount(): Int = cryptocurrencyList.size
        override fun getPageTitle(position: Int) = cryptocurrencyList[position]
    }
}
