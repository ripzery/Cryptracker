package com.ripzery.cryptracker.pages.price

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v4.content.ContextCompat
import android.support.v4.view.animation.FastOutSlowInInterpolator
import android.support.v7.app.AppCompatActivity
import android.util.TypedValue
import android.view.Menu
import android.view.MenuItem
import android.view.animation.Animation
import android.view.animation.RotateAnimation
import android.widget.ImageView
import com.ripzery.cryptracker.R
import com.ripzery.cryptracker.network.DataSource
import com.ripzery.cryptracker.pages.price.cryptocurrency.CryptocurrencyFragment
import com.ripzery.cryptracker.pages.setting.PreferenceActivity
import com.ripzery.cryptracker.services.FirestoreService
import com.ripzery.cryptracker.utils.CurrencyContants
import com.ripzery.cryptracker.utils.SharePreferenceHelper
import kotlinx.android.synthetic.main.activity_price.*
import kotlinx.android.synthetic.main.layout_toolbar.*

class PriceActivity : AppCompatActivity() {

    private var mCryptocurrencyList: MutableList<String> = mutableListOf("omisego", "everex", "ethereum", "bitcoin")
    private lateinit var mPagerAdapter: PricePagerAdapter
    private val MAX_ITEMS_TAB_LAYOUT_FIXED = 4
    private val SAVED_STATE_CRYPTO_LIST = "cryptocurrency_list"
    private var mFirstTime = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_price)

        mCryptocurrencyList = savedInstanceState?.getStringArrayList(SAVED_STATE_CRYPTO_LIST)
                ?: SharePreferenceHelper.readCryptocurrencySetting().toMutableList()
        initInstance()
    }

    private fun initInstance() {

        setSupportActionBar(toolbar)
        supportActionBar?.title = ""
        mPagerAdapter = PricePagerAdapter(mCryptocurrencyList, supportFragmentManager)
        viewPager.adapter = mPagerAdapter
        tabLayout.setupWithViewPager(viewPager)
        handleTabLayoutMode(mCryptocurrencyList.size)
        Handler().postDelayed({
            appBar.setExpanded(false, true)
        }, 700)
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        outState?.putStringArrayList(SAVED_STATE_CRYPTO_LIST, ArrayList(mCryptocurrencyList))
        super.onSaveInstanceState(outState)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_price, menu)
        val outValue = TypedValue()
        theme.resolveAttribute(android.R.attr.selectableItemBackgroundBorderless, outValue, true)

        val settingIcon = menu.findItem(R.id.setting).actionView as ImageView
        settingIcon.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_settings_24dp))
        settingIcon.isClickable = true
        settingIcon.setBackgroundResource(outValue.resourceId)

        val rotateAnim = RotateAnimation(0f, 180f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f)
        rotateAnim.duration = 500
        rotateAnim.interpolator = FastOutSlowInInterpolator()

        appBar.addOnOffsetChangedListener { _, verticalOffset ->
            if (verticalOffset == 0 && mFirstTime) {
                settingIcon.startAnimation(rotateAnim)
                mFirstTime = false
            } else if (verticalOffset < -50) {
                mFirstTime = true
            }
        }

        settingIcon.setOnClickListener {
            mFirstTime = false
            startActivityForResult(Intent(this, PreferenceActivity::class.java), 100)
        }
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

    private fun handleTabLayoutMode(tabLength: Int) {
        tabLayout.tabMode = if (tabLength < MAX_ITEMS_TAB_LAYOUT_FIXED) TabLayout.MODE_FIXED else TabLayout.MODE_SCROLLABLE
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                100 -> {
                    mCryptocurrencyList = SharePreferenceHelper.readCryptocurrencySetting().toMutableList()
                    mPagerAdapter.cryptocurrencyList.clear()
                    mPagerAdapter.cryptocurrencyList.addAll(mCryptocurrencyList)
                    mPagerAdapter.notifyDataSetChanged()
                    handleTabLayoutMode(mCryptocurrencyList.size)
                }
            }
        }
    }

    override fun onStop() {
        super.onStop()
        if (DataSource.lastPriceOmiseGo != null) {
            FirestoreService.startActionSetLastSeenPriceOMG(this, DataSource.lastPriceOmiseGo!!)
            SharePreferenceHelper.writeDouble(CurrencyContants.OMG, DataSource.lastPriceOmiseGo!!.second)
        }

        if (DataSource.lastPriceEvx != null) {
            FirestoreService.startActionSetLastSeenPriceEVX(this, DataSource.lastPriceEvx!!)
            SharePreferenceHelper.writeDouble(CurrencyContants.EVX, DataSource.lastPriceEvx!!.second)
        }
    }

    class PricePagerAdapter(var cryptocurrencyList: MutableList<String>, fm: FragmentManager) : FragmentStatePagerAdapter(fm) {
        override fun getItem(position: Int): Fragment {
            val currencyTop = SharePreferenceHelper.readCurrencyTop()
            val currencyBottom = SharePreferenceHelper.readCurrencyBottom()
            return CryptocurrencyFragment.newInstance(cryptocurrencyList[position], currencyTop, currencyBottom)
        }

        override fun getCount(): Int = cryptocurrencyList.size
        override fun getPageTitle(position: Int) = cryptocurrencyList[position]
        override fun getItemPosition(`object`: Any?) = POSITION_NONE
    }
}
