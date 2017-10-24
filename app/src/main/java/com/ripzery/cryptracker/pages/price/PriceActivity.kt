package com.ripzery.cryptracker.pages.price

import android.os.Bundle
import android.os.Handler
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.ripzery.cryptracker.R
import com.ripzery.cryptracker.network.DataSource
import com.ripzery.cryptracker.services.FirestoreService
import com.ripzery.cryptracker.utils.CurrencyContants
import com.ripzery.cryptracker.utils.SharePreferenceHelper
import kotlinx.android.synthetic.main.activity_price.*
import kotlinx.android.synthetic.main.layout_toolbar.*

class PriceActivity : AppCompatActivity() {

    private val mCryptocurrencyList: List<String> = listOf("omisego", "everex", "ethereum", "bitcoin")
    private val mPagerAdapter: PricePagerAdapter by lazy { PricePagerAdapter(mCryptocurrencyList, supportFragmentManager) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_price)

        initInstance()
    }

    private fun initInstance() {
        setSupportActionBar(toolbar)
        supportActionBar?.title = ""
        viewPager.adapter = mPagerAdapter
        tabLayout.setupWithViewPager(viewPager)
        Handler().postDelayed({
            appBar.setExpanded(false, true)
        }, 700)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_price, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item!!.itemId) {
            R.id.setting -> {
                Log.d("Test", "Setting")
                return true
            }
            else -> false
        }
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
