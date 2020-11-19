package com.example.keepmeout_ui.ui.devicelock

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.keepmeout_ui.R
import com.example.keepmeout_ui.ui.schedulelock.ScheduleLockFragment

val TAB_TITLES = arrayOf(
    R.string.title_device_lock,
    R.string.title_scheduled
)

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
class SectionsPagerAdapter(private val context: Context, fragmentActivity: FragmentActivity) :
    FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return if (position == 0)
            DeviceLockFragment()
        else
            ScheduleLockFragment()
    }
}