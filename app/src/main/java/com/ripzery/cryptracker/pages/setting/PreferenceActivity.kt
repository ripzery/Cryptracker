package com.ripzery.cryptracker.pages.setting

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.ripzery.cryptracker.R
import com.ripzery.cryptracker.pages.setting.manage_crypto.ManageCryptoActivity
import kotlinx.android.synthetic.main.activity_preference.*

class PreferenceActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_preference)
        initInstance()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            android.R.id.home -> finish()
        }
        return true
    }

    private fun initInstance() {
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Preference"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        layoutManageCurrency.setOnClickListener { startActivity(Intent(this, ManageCryptoActivity::class.java)) }
    }
}
