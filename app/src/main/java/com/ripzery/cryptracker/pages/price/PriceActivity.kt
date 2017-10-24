package com.ripzery.cryptracker.pages.price

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.ripzery.cryptracker.R
import com.ripzery.cryptracker.extensions.TAG
import com.ripzery.cryptracker.network.DataSource
import com.ripzery.cryptracker.pages.setting.PreferenceActivity
import com.ripzery.cryptracker.services.FirestoreService
import com.ripzery.cryptracker.utils.CurrencyContants
import com.ripzery.cryptracker.utils.SharePreferenceHelper
import kotlinx.android.synthetic.main.activity_price.*
import kotlinx.android.synthetic.main.layout_toolbar.*

class PriceActivity : AppCompatActivity() {

    private var mCryptocurrencyList: MutableList<String> = mutableListOf("omisego", "everex", "ethereum", "bitcoin")
    private lateinit var mPagerAdapter: PricePagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_price)

        initInstance()
    }

    private fun initInstance() {
        setSupportActionBar(toolbar)
        supportActionBar?.title = ""

        mCryptocurrencyList = SharePreferenceHelper.readCryptocurrencySetting().toMutableList()

        mPagerAdapter = PricePagerAdapter(mCryptocurrencyList, supportFragmentManager)
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
                startActivityForResult(Intent(this, PreferenceActivity::class.java), 100)
                return true
            }
            else -> false
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                100 -> {
                    mCryptocurrencyList = SharePreferenceHelper.readCryptocurrencySetting().toMutableList()
                    mPagerAdapter.cryptocurrencyList.clear()
                    mPagerAdapter.cryptocurrencyList.addAll(mCryptocurrencyList)
                    mPagerAdapter.notifyDataSetChanged()
                }
            }
        }
    }

    override fun onStop() {
        super.onStop()
        if (DataSource.lastPriceOmiseGo != null) {
            FirestoreService.startActionSetLastSeenPriceOMG(this, DataSource.lastPriceOmiseGo!!)
            SharePreferenceHelper.writeDouble(CurrencyContants.OMG, DataSource.lastPriceOmiseGo?.second)
            SharePreferenceHelper.writeDouble(CurrencyContants.EVX, DataSource.lastPriceEvx?.second)
        }

        if (DataSource.lastPriceEvx != null) {
            FirestoreService.startActionSetLastSeenPriceEVX(this, DataSource.lastPriceEvx!!)
        }
    }

    class PricePagerAdapter(var cryptocurrencyList: MutableList<String>, fm: FragmentManager) : FragmentStatePagerAdapter(fm) {
        override fun getItem(position: Int) = PriceFragment.newInstance(cryptocurrencyList[position])
        override fun getCount(): Int = cryptocurrencyList.size
        override fun getPageTitle(position: Int) = cryptocurrencyList[position]
        override fun getItemPosition(`object`: Any?) = POSITION_NONE
    }
}
