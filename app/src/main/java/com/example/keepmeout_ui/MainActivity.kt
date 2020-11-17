package com.example.keepmeout_ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.keepmeout_ui.databinding.ActivityMainBinding
import com.example.keepmeout_ui.ui.main.SectionsPagerAdapter
import com.example.keepmeout_ui.ui.main.TAB_TITLES
import com.google.android.material.tabs.TabLayoutMediator


class MainActivity : AppCompatActivity() {
    private lateinit var viewBinding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        val sectionsPagerAdapter = SectionsPagerAdapter(this, this)
        viewBinding.viewPager.adapter = sectionsPagerAdapter

        TabLayoutMediator(viewBinding.tabLayout, viewBinding.viewPager) { tab, position ->
            tab.text = getPageTitle(position)
        }.attach()

        setSupportActionBar(viewBinding.toolbar)
    }

    private fun getPageTitle(position: Int): CharSequence? {
        return resources.getString(TAB_TITLES[position])
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection
        when (item.itemId) {
            R.id.action_settings -> {
                /*NA*/
            }
            R.id.action_rate -> {
                /*NA*/
            }
            R.id.nav_fbPage -> {
                /*NA*/
            }
            else -> super.onOptionsItemSelected(item)
        }
        return true
    }
}