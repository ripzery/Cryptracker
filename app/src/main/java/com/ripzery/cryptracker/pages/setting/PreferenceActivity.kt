package com.ripzery.cryptracker.pages.setting

import android.app.Activity
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.ripzery.cryptracker.R
import com.ripzery.cryptracker.pages.setting.manage_crypto.GeneralPreferenceFragment
import kotlinx.android.synthetic.main.activity_preference.*

class PreferenceActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_preference)
        initInstance()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            android.R.id.home -> {
                setResult(Activity.RESULT_OK)
                finish()
            }
        }
        return true
    }

    override fun onBackPressed() {
        setResult(Activity.RESULT_OK)
        finish()
    }

    private fun initInstance() {
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Setting"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportFragmentManager.beginTransaction().replace(R.id.fragmentContainer, GeneralPreferenceFragment()).commit()
    }
}
