package com.ripzery.cryptracker.pages.setting.manage_crypto

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.view.View
import com.ripzery.cryptracker.R
import kotlinx.android.synthetic.main.fragment_manage_cryptocurrency.*
import kotlinx.android.synthetic.main.layout_toolbar.*

class ManageCryptoActivity : AppCompatActivity() {
    private val tabs = listOf("Active", "Inactive")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manage_crypto)
        initInstance()
    }

    private fun initInstance() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Manage"
        icon.visibility = View.GONE
        tvTitle.visibility = View.GONE

        val adapter = ManageCryptoAdapter(tabs, supportFragmentManager)
        viewPager.adapter = adapter
        tabLayout.setupWithViewPager(viewPager)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            android.R.id.home -> finish()
        }
        return true
    }

    /* Inner class zone */
    class ManageCryptoAdapter(private val tabList: List<String>, fm: FragmentManager) : FragmentPagerAdapter(fm) {
        override fun getItem(position: Int): Fragment = if (position == 0) CryptoListFragment.newInstance(true) else CryptoListFragment.newInstance(false)
        override fun getCount() = tabList.size
        override fun getPageTitle(position: Int) = tabList[position]
    }
}
