package com.example.keepmeout_ui.adapter

import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import com.example.keepmeout_ui.R
import github.hellocsl.cursorwheel.CursorWheelLayout.CycleWheelAdapter

/**
 *
 */
class SimpleTextAdapter constructor(
    private val mContext: Context,
    private val mMenuItemData: List<String>,
    private val mGravity: Int = Gravity.CENTER
) : CycleWheelAdapter() {

    override fun getCount(): Int = mMenuItemData.size

    override fun getView(parent: View?, position: Int): View {
        val mTitle = getItem(position)
        val mLayoutInflater: LayoutInflater = LayoutInflater.from(mContext)
        val root = mLayoutInflater.inflate(R.layout.wheel_menu_item, null, false)
        val textView = root.findViewById<View>(R.id.wheel_menu_item_tv) as TextView
        textView.visibility = View.VISIBLE
        textView.text = mTitle
        if (textView.layoutParams is FrameLayout.LayoutParams) {
            (textView.layoutParams as FrameLayout.LayoutParams).gravity = mGravity
        }
        return root
    }

    override fun getItem(position: Int): String {
        return mMenuItemData[position]
    }
}