package com.ripzery.cryptracker.pages.price

import android.app.Activity
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
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
import com.ripzery.cryptracker.extensions.getViewModel
import com.ripzery.cryptracker.pages.price.cryptocurrency.CryptocurrencyFragment
import com.ripzery.cryptracker.pages.setting.PreferenceActivity
import com.ripzery.cryptracker.utils.SharePreferenceHelper
import kotlinx.android.synthetic.main.activity_price.*
import kotlinx.android.synthetic.main.layout_toolbar.*

class PriceActivity : AppCompatActivity() {

    private var mCryptocurrencyList: MutableList<String> = mutableListOf("omisego", "everex", "ethereum", "bitcoin")
    private val mViewModel by lazy { getViewModel(PriceViewModel::class.java) }
    private val mPagerAdapter: PricePagerAdapter by lazy { PricePagerAdapter(mCryptocurrencyList, fm = supportFragmentManager) }
    private val MAX_ITEMS_TAB_LAYOUT_FIXED = 4
    private val SAVED_STATE_CRYPTO_LIST = "cryptocurrency_list"
    private var mFirstTime = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_price)

        mViewModel.init(savedInstanceState)
        mViewModel.getCryptocurrencyList().observe(this, mCryptoListObserver)
        initInstance()
    }

    private val mCryptoListObserver = Observer<MutableList<String>> {
        mCryptocurrencyList = it!!
        mPagerAdapter.cryptocurrencyList.clear()
        mPagerAdapter.cryptocurrencyList.addAll(mCryptocurrencyList)
        mPagerAdapter.notifyDataSetChanged()
        handleTabLayoutMode(mCryptocurrencyList.size)
    }

    private fun initInstance() {
        lifecycle.addObserver(mViewModel)

        setSupportActionBar(toolbar)
        supportActionBar?.title = ""

        viewPager.adapter = mPagerAdapter
        tabLayout.setupWithViewPager(viewPager)

        Handler().postDelayed({
            appBar.setExpanded(false, true)
        }, 700)
    }

    /* savedInstanceState is still needed no matter we're already use architecture components :(
     * In case of the app is killed by the system, the viewmodel is no longer persist anymore.
     */
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
                100 -> mViewModel.refresh()
            }
        }
    }

    class PricePagerAdapter(var cryptocurrencyList: MutableList<String>,
                            fm: FragmentManager) : FragmentStatePagerAdapter(fm) {
        override fun getItem(position: Int): Fragment = CryptocurrencyFragment.newInstance(cryptocurrencyList[position])
        override fun getCount(): Int = cryptocurrencyList.size
        override fun getPageTitle(position: Int) = cryptocurrencyList[position]
        override fun getItemPosition(`object`: Any?) = POSITION_NONE
    }
}
