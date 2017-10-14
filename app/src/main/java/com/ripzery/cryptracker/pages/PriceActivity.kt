package com.ripzery.cryptracker.pages

import android.os.Bundle
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v7.app.AppCompatActivity
import com.ripzery.cryptracker.R
import kotlinx.android.synthetic.main.activity_price.*

class PriceActivity : AppCompatActivity() {

    private val mCryptocurrencyList: List<String> = listOf("omisego", "everex")
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

    class PricePagerAdapter(private val cryptocurrencyList: List<String>, fm: FragmentManager) : FragmentPagerAdapter(fm) {
        override fun getItem(position: Int) = PriceFragment.newInstance(cryptocurrencyList[position])
        override fun getCount(): Int = cryptocurrencyList.size
        override fun getPageTitle(position: Int) = cryptocurrencyList[position]
    }
}
